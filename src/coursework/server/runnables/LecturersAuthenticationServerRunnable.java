package coursework.server.runnables;

import coursework.client.runnables.StudentsClientRunnable;
import coursework.common.Configuration;
import coursework.common.Signature;
import coursework.common.model.SignedObject;
import coursework.common.model.Solution;
import coursework.server.Server;

import java.net.InetAddress;

/**
 * @author adkozlov
 */
public class LecturersAuthenticationServerRunnable extends AuthenticationServerRunnable {

    public LecturersAuthenticationServerRunnable(Server server) {
        super(server);
    }

    @Override
    protected void writeAll(InetAddress address, Signature signature) {
        Server server = getServer();
        writeAll(address, signature, server.getSolutions(), server.getSentSolutions());
    }

    @Override
    protected StudentsClientRunnable newClientRunnable(InetAddress address, SignedObject signedObject) {
        return new StudentsClientRunnable(address, Configuration.LECTURERS_SERVER_PORT, (Solution) signedObject);
    }

    @Override
    protected int getPort() {
        return Configuration.LECTURERS_AUTHENTICATION_PORT;
    }

    @Override
    public boolean isStudentsObject() {
        return false;
    }
}
