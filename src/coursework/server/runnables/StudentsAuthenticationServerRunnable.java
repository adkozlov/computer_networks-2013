package coursework.server.runnables;

import coursework.client.runnables.LecturersClientRunnable;
import coursework.common.Configuration;
import coursework.common.Signature;
import coursework.common.model.SignedObject;
import coursework.server.Server;

import java.net.InetAddress;

/**
 * @author adkozlov
 */
public class StudentsAuthenticationServerRunnable extends AuthenticationServerRunnable {

    public StudentsAuthenticationServerRunnable(Server server) {
        super(server);
    }

    @Override
    protected void writeAll(InetAddress address, Signature signature) {
        writeTasks(address, signature);
        writeVerdicts(address, signature);
    }

    @Override
    protected LecturersClientRunnable newClientRunnable(InetAddress address, SignedObject signedObject) {
        return new LecturersClientRunnable(address, Configuration.STUDENTS_SERVER_PORT, signedObject);
    }

    @Override
    protected int getPort() {
        return Configuration.STUDENTS_AUTHENTICATION_PORT;
    }

    @Override
    public boolean isStudentsObject() {
        return true;
    }
}
