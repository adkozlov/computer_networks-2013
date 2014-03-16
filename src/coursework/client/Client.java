package coursework.client;

import coursework.client.runnables.AuthenticationClientRunnable;
import coursework.common.Logger;
import coursework.common.Utils;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.AuthenticationResponse;

/**
 * @author adkozlov
 */
public abstract class Client implements Runnable {

    private static final long SLEEP_PERIOD = 250;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        AuthenticationClientRunnable authenticationClientRunnable = new AuthenticationClientRunnable(authenticationRequest);

        Utils.startRunnable(authenticationClientRunnable);

        while (authenticationClientRunnable.getAuthenticationResponse() == null) {
            try {
                Thread.sleep(SLEEP_PERIOD);
            } catch (InterruptedException e) {
                Logger.getInstance().logException(e);
            }
        }

        return authenticationClientRunnable.getAuthenticationResponse();
    }
}
