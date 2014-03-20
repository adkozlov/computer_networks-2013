package coursework.client.runnables;

import coursework.common.messages.AuthenticationRequestMessage;
import coursework.common.messages.AuthenticationResponseMessage;
import coursework.common.messages.IMessage;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.AuthenticationResponse;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public abstract class AuthenticationClientRunnable extends ClientRunnable {

    private final AuthenticationRequest authenticationRequest;
    private AuthenticationResponse authenticationResponse;

    public AuthenticationClientRunnable(AuthenticationRequest authenticationRequest) {
        this.authenticationRequest = authenticationRequest;
    }

    public AuthenticationResponse getAuthenticationResponse() {
        return authenticationResponse;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        writeMessage(socket, new AuthenticationRequestMessage(authenticationRequest));

        authenticationResponse = readMessage(readBytes(socket)).getAuthenticationResponse();
    }

    protected abstract void writeMessageFile(IMessage message) throws IOException;

    @Override
    protected AuthenticationResponseMessage readMessage(byte[] bytes) throws IOException {
        return new AuthenticationResponseMessage(bytes);
    }
}
