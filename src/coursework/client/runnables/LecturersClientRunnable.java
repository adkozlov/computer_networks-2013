package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.messages.IMessage;
import coursework.common.messages.SolutionMessage;
import coursework.common.messages.TaskMessage;
import coursework.common.messages.VerdictMessage;
import coursework.common.model.Task;
import coursework.common.model.Verdict;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * @author adkozlov
 */
public class LecturersClientRunnable extends ClientRunnable {

    private final Task task;
    private final Verdict verdict;

    public LecturersClientRunnable(Task task) {
        this.task = task;
        verdict = null;
    }

    public LecturersClientRunnable(Verdict verdict) {
        task = null;
        this.verdict = verdict;
    }

    @Override
    protected int getPort() {
        return Configuration.LECTURERS_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        if (task != null) {
            writeMessage(socket, new TaskMessage(task));
        } else if (verdict != null) {
            writeMessage(socket, new VerdictMessage(verdict));
        } else {
            List<IMessage> solutions = readMessages(socket);

            for (IMessage message : solutions) {
                if (message instanceof SolutionMessage) {
                    writeSolution(((SolutionMessage) message).getSolution());
                } else {
                    throw new IMessage.UnexpectedMessageException(message);
                }
            }
        }
    }

    @Override
    protected SolutionMessage readMessage(byte[] bytes) throws IOException {
        return new SolutionMessage(bytes);
    }

    @Override
    protected String getFilePath() {
        return Configuration.LECTURER_FILES_PATH;
    }

    @Override
    public boolean isStudentsObject() {
        return false;
    }
}
