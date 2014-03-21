package coursework.server.runnables;

import coursework.common.Configuration;
import coursework.common.Signature;
import coursework.common.UsersContainer;
import coursework.common.model.SignedObject;
import coursework.server.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class SynchronizationAuthenticationServerRunnable extends AuthenticationServerRunnable {

    public SynchronizationAuthenticationServerRunnable(Server server) {
        super(server);
    }

    @Override
    public void run() {

    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        Signature signature = UsersContainer.getInstance().getSignature(getFilePath());
        writeAll(socket.getInetAddress(), signature);
    }

    @Override
    protected void writeAll(InetAddress address, Signature signature) {
        writeTasks(address, signature);
        writeSolution(address, signature);
        writeVerdicts(address, signature);
    }

    @Override
    protected SynchronizationClientRunnable newClientRunnable(InetAddress address, SignedObject signedObject) {
        return new SynchronizationClientRunnable(address, getServer().getServerId(), signedObject);
    }

    @Override
    protected int getPort() {
        return Configuration.SYNCHRONIZATION_PORT;
    }

    @Override
    public boolean isStudentsObject() {
        return false;
    }
}
