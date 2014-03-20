package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.model.AuthenticationRequest;

/**
 * @author adkozlov
 */
public class LecturersAuthenticationClientRunnable extends AuthenticationClientRunnable {

    public LecturersAuthenticationClientRunnable(AuthenticationRequest authenticationRequest) {
        super(authenticationRequest);
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
