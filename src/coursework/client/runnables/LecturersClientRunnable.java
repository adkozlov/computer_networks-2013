package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.messages.IMessage;
import coursework.common.messages.SolutionResponseMessage;
import coursework.common.messages.TaskMessage;
import coursework.common.model.SolutionResponse;
import coursework.common.model.Task;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class LecturersClientRunnable extends ClientRunnable {

    private final Task task;
    private final SolutionResponse solutionResponse;

    public LecturersClientRunnable(Task task) {
        this.task = task;
        solutionResponse = null;
    }

    public LecturersClientRunnable(SolutionResponse solutionResponse) {
        task = null;
        this.solutionResponse = solutionResponse;
    }

    @Override
    protected int getPort() {
        return Configuration.LECTURERS_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        IMessage message = task != null ? new TaskMessage(task) : new SolutionResponseMessage(solutionResponse);

        writeMessage(socket, message);
    }
}
