package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.messages.IMessage;
import coursework.common.messages.TaskMessage;
import coursework.common.messages.VerdictMessage;
import coursework.common.model.AuthenticationRequest;

import java.io.IOException;

/**
 * @author adkozlov
 */
public class StudentsAuthenticationClientRunnable extends AuthenticationClientRunnable {

    public StudentsAuthenticationClientRunnable(AuthenticationRequest authenticationRequest) {
        super(authenticationRequest);
    }

    @Override
    protected void writeMessageFile(IMessage message) throws IOException {
        if (message instanceof TaskMessage) {
            writeTask(((TaskMessage) message).getTask());
        } else if (message instanceof VerdictMessage) {
            writeVerdict(((VerdictMessage) message).getVerdict());
        } else {
            throw new IMessage.UnexpectedMessageException(message);
        }
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
