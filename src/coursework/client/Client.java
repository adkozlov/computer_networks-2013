package coursework.client;

import coursework.client.runnables.AuthenticationClientRunnable;
import coursework.common.Utils;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.AuthenticationResponse;

/**
 * @author adkozlov
 */
public abstract class Client implements Runnable {

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        AuthenticationClientRunnable authenticationClientRunnable = new AuthenticationClientRunnable(authenticationRequest);

        Utils.startRunnable(authenticationClientRunnable);

        return authenticationClientRunnable.getAuthenticationResponse();
    }
}
