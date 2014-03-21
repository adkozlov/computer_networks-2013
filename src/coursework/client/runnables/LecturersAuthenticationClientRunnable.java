package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.model.AuthenticationRequest;

import java.net.InetAddress;

/**
 * @author adkozlov
 */
public class LecturersAuthenticationClientRunnable extends AuthenticationClientRunnable {

    public LecturersAuthenticationClientRunnable(InetAddress address, AuthenticationRequest authenticationRequest) {
        super(address, authenticationRequest);
    }

    @Override
    public boolean isStudentsObject() {
        return false;
    }

    @Override
    protected int getPort() {
        return Configuration.LECTURERS_AUTHENTICATION_PORT;
    }

    @Override
    protected String getFilePath() {
        return Configuration.LECTURER_FILES_PATH;
    }
}
