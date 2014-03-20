package coursework.client;

import coursework.client.runnables.AuthenticationClientRunnable;
import coursework.client.runnables.LecturersAuthenticationClientRunnable;
import coursework.client.runnables.StudentsAuthenticationClientRunnable;
import coursework.common.Logger;
import coursework.common.Signature;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.AuthenticationResponse;

/**
 * @author adkozlov
 */
public abstract class Client extends Thread implements IGroupable {

    private static final long DEFAULT_SLEEP_PERIOD = 250;

    private Signature signature;

    public Signature getSignature() {
        return signature;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        AuthenticationClientRunnable authenticationClientRunnable = isStudentsObject() ? new StudentsAuthenticationClientRunnable(authenticationRequest) : new LecturersAuthenticationClientRunnable(authenticationRequest);

        authenticationClientRunnable.start();
        while (authenticationClientRunnable.getAuthenticationResponse() == null) {
            try {
                sleep(DEFAULT_SLEEP_PERIOD);
            } catch (InterruptedException e) {
                Logger.getInstance().logException(e);
            }
        }

        AuthenticationResponse authenticationResponse = authenticationClientRunnable.getAuthenticationResponse();
        signature = authenticationResponse.getSignature();

        return authenticationResponse;
    }
}
