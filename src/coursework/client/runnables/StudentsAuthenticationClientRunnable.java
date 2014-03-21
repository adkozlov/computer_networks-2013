package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.model.AuthenticationRequest;

import java.net.InetAddress;

/**
 * @author adkozlov
 */
public class StudentsAuthenticationClientRunnable extends AuthenticationClientRunnable {

    public StudentsAuthenticationClientRunnable(InetAddress address, AuthenticationRequest authenticationRequest) {
        super(address, authenticationRequest);
    }

    @Override
    public boolean isStudentsObject() {
        return true;
    }

    @Override
    protected int getPort() {
        return Configuration.STUDENTS_AUTHENTICATION_PORT;
    }

    @Override
    protected String getFilePath() {
        return Configuration.STUDENT_FILES_PATH;
    }
}
