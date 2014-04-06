package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.Signature;
import coursework.common.messages.AbstractMessage;
import coursework.common.messages.IMessage;
import coursework.common.messages.TaskMessage;
import coursework.common.messages.VerdictMessage;
import coursework.common.runnables.AbstractStudentFilesServerRunnable;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class StudentFilesServerRunnable extends AbstractStudentFilesServerRunnable {

    public StudentFilesServerRunnable() {
        super(null);
    }

    @Override
    protected int getPort() {
        return Configuration.STUDENTS_SERVER_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        IMessage message = readMessage(readBytes(socket));

        if (message instanceof TaskMessage) {
            writeTask(((TaskMessage) message).getTask());
        } else if (message instanceof VerdictMessage) {
            writeVerdict(((VerdictMessage) message).getVerdict());
        } else {
            throw new IMessage.UnexpectedMessageException(message);
        }
    }

    @Override
    protected IMessage readMessage(byte[] bytes) throws IOException {
        byte type = bytes[Configuration.INT_BYTES_LENGTH];

        switch (type) {
            case TaskMessage.TYPE:
                return new TaskMessage(bytes);
            case VerdictMessage.TYPE:
                return new VerdictMessage(bytes);
        }

        throw new AbstractMessage.MessageTypeRecognizingException(type);
    }

    @Override
    public boolean isStudentsObject() {
        return true;
    }

    @Override
    protected String getFilePath(Signature signature) {
        return Configuration.STUDENT_FILES_PATH;
    }
}
