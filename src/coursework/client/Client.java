package coursework.client;

import coursework.client.runnables.AuthenticationClientRunnable;
import coursework.client.runnables.ClientRunnable;
import coursework.common.Signature;
import coursework.common.Utils;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.AuthenticationResponse;
import coursework.common.runnables.SleepableRunnable;

/**
 * @author adkozlov
 */
public abstract class Client extends SleepableRunnable {

    private Signature signature;

    public Signature getSignature() {
        return signature;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        AuthenticationClientRunnable authenticationClientRunnable = new AuthenticationClientRunnable(authenticationRequest);

        Utils.startRunnable(authenticationClientRunnable);
        while (authenticationClientRunnable.getAuthenticationResponse() == null) {
            sleep();
        }


        AuthenticationResponse authenticationResponse = authenticationClientRunnable.getAuthenticationResponse();
        signature = authenticationResponse.getSignature();

        return authenticationResponse;
    }

    protected void startClientRunnable(ClientRunnable clientRunnable) {
        Utils.startRunnable(clientRunnable);
    }
}
