package coursework.server.runnables;

import coursework.common.Configuration;
import coursework.common.messages.AbstractMessage;
import coursework.common.messages.IMessage;
import coursework.common.messages.TaskMessage;
import coursework.common.messages.VerdictMessage;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class LecturersServerRunnable extends ServerRunnable {

    @Override
    protected int getPort() {
        return Configuration.LECTURERS_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        byte[] bytes = readBytes(socket);

        IMessage message = readMessage(bytes);
        if (message instanceof TaskMessage) {
            writeTask(((TaskMessage) message).getTask());
        } else if (message instanceof VerdictMessage) {
            writeVerdict(((VerdictMessage) message).getVerdict());
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
}
