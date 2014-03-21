package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.Logger;
import coursework.common.messages.AuthenticationRequestMessage;
import coursework.common.messages.AuthenticationResponseMessage;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.AuthenticationResponse;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author adkozlov
 */
public abstract class AuthenticationClientRunnable extends ClientRunnable {

    @Override
    public void run() {
        while (true) {
            super.run();

            try {
                sleep(Configuration.AUTO_UPDATE_PERIOD);
            } catch (InterruptedException e) {
                Logger.getInstance().logException(e);
            }
        }
    }

    private final AuthenticationRequest authenticationRequest;
    private AuthenticationResponse authenticationResponse;

    public AuthenticationClientRunnable(InetAddress address, AuthenticationRequest authenticationRequest) {
        super(address);
        this.authenticationRequest = authenticationRequest;
    }

    protected AuthenticationClientRunnable(InetAddress address, int port) {
        super(address, port);
        authenticationRequest = null;
    }

    public AuthenticationResponse getAuthenticationResponse() {
        return authenticationResponse;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        writeMessage(socket, new AuthenticationRequestMessage(authenticationRequest));

        authenticationResponse = readMessage(readBytes(socket)).getAuthenticationResponse();
    }

    @Override
    protected AuthenticationResponseMessage readMessage(byte[] bytes) throws IOException {
        return new AuthenticationResponseMessage(bytes);
    }
}
