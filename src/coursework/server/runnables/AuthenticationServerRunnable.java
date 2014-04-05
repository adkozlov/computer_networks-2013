package coursework.server.runnables;

import coursework.client.runnables.ClientRunnable;
import coursework.common.Signature;
import coursework.common.UsersContainer;
import coursework.common.messages.AuthenticationRequestMessage;
import coursework.common.messages.AuthenticationResponseMessage;
import coursework.common.messages.IMessage;
import coursework.common.model.AuthenticationRequest;
import coursework.common.model.AuthenticationResponse;
import coursework.common.model.SignedObject;
import coursework.server.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

/**
 * @author adkozlov
 */
public abstract class AuthenticationServerRunnable extends ServerRunnable {

    public AuthenticationServerRunnable(Server server) {
        super(server);
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        AuthenticationRequest request = ((AuthenticationRequestMessage) readMessage(readBytes(socket))).getAuthenticationRequest();

        boolean passed = UsersContainer.getInstance().isAuthenticationPassed(request.isStudent(), request.getLogin(), request.getPasswordHashCode());
        Signature signature = null;

        AuthenticationResponse authenticationResponse;
        if (passed) {
            String login = request.getLogin();
            signature = UsersContainer.getInstance().getSignature(login);

            authenticationResponse = new AuthenticationResponse(UsersContainer.getInstance().getName(login), signature);
        } else {
            authenticationResponse = new AuthenticationResponse();
        }

        writeMessage(socket, new AuthenticationResponseMessage(authenticationResponse));

        if (passed) {
            writeAll(new Connection(socket.getInetAddress(), signature));
        }
    }

    protected abstract void writeAll(Connection connection);

    protected <E extends SignedObject> void writeAll(Connection connection, Map<Signature, E> signedObjects, Map<Connection, Set<E>> sentSignedObjects) {
        for (Map.Entry<Signature, E> entry : signedObjects.entrySet()) {
            E signedObject = entry.getValue();

            if (!Server.isSent(connection, signedObject, sentSignedObjects)) {
                newClientRunnable(connection.getAddress(), signedObject).start();
                Server.send(connection, signedObject, sentSignedObjects);
            }
        }
    }

    protected void writeTasks(Connection connection) {
        Server server = getServer();
        writeAll(connection, server.getTasks(), server.getSentTasks());
    }

    protected void writeSolution(Connection connection) {
        Server server = getServer();
        writeAll(connection, server.getSolutions(), server.getSentSolutions());
    }

    protected void writeVerdicts(Connection connection) {
        Server server = getServer();
        writeAll(connection, server.getVerdicts(), server.getSentVerdicts());
    }

    protected abstract ClientRunnable newClientRunnable(InetAddress address, SignedObject signedObject);

    @Override
    protected IMessage readMessage(byte[] bytes) throws IOException {
        return new AuthenticationRequestMessage(bytes);
    }
}