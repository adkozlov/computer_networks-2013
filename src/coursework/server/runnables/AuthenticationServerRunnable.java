package coursework.server.runnables;

import coursework.client.runnables.ClientRunnable;
import coursework.common.Signature;
import coursework.common.UsersContainer;
import coursework.common.messages.AuthenticationRequestMessage;
import coursework.common.messages.AuthenticationResponseMessage;
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
        AuthenticationRequest request = readMessage(readBytes(socket)).getAuthenticationRequest();

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
            writeAll(socket.getInetAddress(), signature);
        }
    }

    protected abstract void writeAll(InetAddress address, Signature signature);

    protected <E extends SignedObject> void writeAll(InetAddress address, Signature signature, Map<Signature, E> signedObjects, Map<Signature, Set<E>> sentSignedObjects) {
        for (Map.Entry<Signature, E> entry : signedObjects.entrySet()) {
            E signedObject = entry.getValue();

            if (!getServer().isSent(signature, signedObject, sentSignedObjects)) {
                newClientRunnable(address, signedObject).start();
                getServer().send(signature, signedObject, sentSignedObjects);
            }
        }
    }

    protected void writeTasks(InetAddress address, Signature signature) {
        Server server = getServer();
        writeAll(address, signature, server.getTasks(), server.getSentTasks());
    }

    protected void writeSolution(InetAddress address, Signature signature) {
        Server server = getServer();
        writeAll(address, signature, server.getSolutions(), server.getSentSolutions());
    }

    protected void writeVerdicts(InetAddress address, Signature signature) {
        Server server = getServer();
        writeAll(address, signature, server.getVerdicts(), server.getSentVerdicts());
    }

    protected abstract ClientRunnable newClientRunnable(InetAddress address, SignedObject signedObject);

    @Override
    protected AuthenticationRequestMessage readMessage(byte[] bytes) throws IOException {
        return new AuthenticationRequestMessage(bytes);
    }
}