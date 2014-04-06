package coursework.server.runnables;

import coursework.client.runnables.StudentsClientRunnable;
import coursework.common.Configuration;
import coursework.common.Connection;
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
    protected void writeAll(Connection connection) {
        writeSolutions(connection);
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
