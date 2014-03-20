package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.messages.TaskMessage;
import coursework.common.messages.VerdictMessage;
import coursework.common.model.Task;
import coursework.common.model.Verdict;

import java.io.IOException;
import java.net.Socket;

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

    public LecturersClientRunnable() {
        task = null;
        verdict = null;
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

        }
    }
}
