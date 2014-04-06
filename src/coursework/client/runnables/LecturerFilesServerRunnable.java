package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.messages.AbstractMessage;
import coursework.common.messages.IMessage;
import coursework.common.messages.SolutionMessage;
import coursework.common.runnables.AbstractLecturerFilesServerRunnable;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class LecturerFilesServerRunnable extends AbstractLecturerFilesServerRunnable {

    public LecturerFilesServerRunnable() {
        super(null);
    }

    @Override
    protected int getPort() {
        return Configuration.LECTURERS_SERVER_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        IMessage message = readMessage(readBytes(socket));

        if (message instanceof SolutionMessage) {
            writeSolution(((SolutionMessage) message).getSolution());
        } else {
            throw new IMessage.UnexpectedMessageException(message);
        }
    }

    @Override
    protected IMessage readMessage(byte[] bytes) throws IOException {
        byte type = bytes[Configuration.INT_BYTES_LENGTH];

        switch (type) {
            case SolutionMessage.TYPE:
                return new SolutionMessage(bytes);
        }

        throw new AbstractMessage.MessageTypeRecognizingException(type);
    }

    @Override
    public boolean isStudentsObject() {
        return false;
    }

    @Override
    protected String getFilePath() {
        return Configuration.LECTURER_FILES_PATH;
    }
}
