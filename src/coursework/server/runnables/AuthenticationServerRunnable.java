package coursework.server.runnables;

import coursework.common.Configuration;
import coursework.common.UsersContainer;
import coursework.common.messages.AuthenticationRequestMessage;
import coursework.common.messages.AuthenticationResponseMessage;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.AuthenticationResponse;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class AuthenticationServerRunnable extends ServerRunnable {
    @Override
    protected int getPort() {
        return Configuration.AUTHENTICATION_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        AuthenticationRequest request = new AuthenticationRequestMessage(readBytes(socket)).getAuthenticationRequest();

        AuthenticationResponse response = UsersContainer.getInstance().isAuthenticationPassed(request.isStudent(), request.getLogin(), request.getPasswordHashCode()) ?
                new AuthenticationResponse(UsersContainer.getInstance().getName(request.getLogin())) :
                new AuthenticationResponse();

        writeMessage(socket, new AuthenticationResponseMessage(response));
    }
}