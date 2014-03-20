package coursework.server.runnables;

import coursework.common.UsersContainer;
import coursework.common.messages.AuthenticationRequestMessage;
import coursework.common.messages.AuthenticationResponseMessage;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.AuthenticationResponse;
import coursework.server.Server;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public abstract class AuthenticationServerRunnable extends ServerRunnable {

    public AuthenticationServerRunnable(Server server) {
        super(server);
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        AuthenticationRequest request = readMessage(readBytes(socket)).getAuthenticationRequest();
        boolean passed = UsersContainer.getInstance().isAuthenticationPassed(request.isStudent(), request.getLogin(), request.getPasswordHashCode());

        AuthenticationResponse authenticationResponse;
        if (passed) {
            String login = request.getLogin();

            authenticationResponse = new AuthenticationResponse(UsersContainer.getInstance().getName(login), UsersContainer.getInstance().getSignature(login));
        } else {
            authenticationResponse = new AuthenticationResponse();
        }

        writeMessage(socket, new AuthenticationResponseMessage(authenticationResponse));
    }

    @Override
    protected AuthenticationRequestMessage readMessage(byte[] bytes) throws IOException {
        return new AuthenticationRequestMessage(bytes);
    }
}