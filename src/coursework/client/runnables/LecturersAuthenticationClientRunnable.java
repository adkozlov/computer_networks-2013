package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.messages.IMessage;
import coursework.common.messages.SolutionMessage;
import coursework.common.model.AuthenticationRequest;

import java.io.IOException;

/**
 * @author adkozlov
 */
public class LecturersAuthenticationClientRunnable extends AuthenticationClientRunnable {

    public LecturersAuthenticationClientRunnable(AuthenticationRequest authenticationRequest) {
        super(authenticationRequest);
    }

    @Override
    protected void writeMessageFile(IMessage message) throws IOException {
        if (message instanceof SolutionMessage) {
            writeSolution(((SolutionMessage) message).getSolution());
        } else {
            throw new IMessage.UnexpectedMessageException(message);
        }
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
