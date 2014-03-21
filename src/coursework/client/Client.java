package coursework.client;

import coursework.client.runnables.*;
import coursework.common.ICleanable;
import coursework.common.Logger;
import coursework.common.Signature;
import coursework.common.Utils;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.AuthenticationResponse;
import coursework.server.runnables.ServerRunnable;

import java.net.InetAddress;

/**
 * @author adkozlov
 */
public abstract class Client extends Thread implements IGroupable, ICleanable {

    private final InetAddress serverAddress;

    protected Client(InetAddress serverAddress) {
        this.serverAddress = serverAddress;

        Utils.cleanDirectory(getStoragePath());
    }

    public InetAddress getServerAddress() {
        return serverAddress;
    }

    private final ServerRunnable SERVER = isStudentsObject() ? new StudentServerRunnable() : new LecturerServerRunnable();

    @Override
    public void run() {
        SERVER.start();
    }

    private static final long DEFAULT_SLEEP_PERIOD = 250;

    private Signature signature;

    public Signature getSignature() {
        return signature;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        AuthenticationClientRunnable authenticationClientRunnable = isStudentsObject() ? new StudentsAuthenticationClientRunnable(serverAddress, authenticationRequest) : new LecturersAuthenticationClientRunnable(serverAddress, authenticationRequest);

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
