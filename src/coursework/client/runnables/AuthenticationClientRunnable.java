package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.messages.AuthenticationRequestMessage;
import coursework.common.messages.AuthenticationResponseMessage;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.AuthenticationResponse;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class AuthenticationClientRunnable extends ClientRunnable {

    private final AuthenticationRequest authenticationRequest;
    private AuthenticationResponse authenticationResponse;

    public AuthenticationClientRunnable(AuthenticationRequest authenticationRequest) {
        this.authenticationRequest = authenticationRequest;
    }

    public AuthenticationResponse getAuthenticationResponse() {
        return authenticationResponse;
    }

    @Override
    protected int getPort() {
        return Configuration.AUTHENTICATION_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        writeMessage(socket, new AuthenticationRequestMessage(authenticationRequest));

        authenticationResponse = new AuthenticationResponseMessage(readBytes(socket)).getAuthenticationResponse();
    }
}
