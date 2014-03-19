package coursework.server.runnables;

import coursework.common.Configuration;
import coursework.common.Signature;
import coursework.common.UsersContainer;
import coursework.common.messages.TaskMessage;
import coursework.common.messages.VerdictMessage;
import coursework.common.model.Task;
import coursework.common.model.Verdict;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        switch (bytes[0]) {
            case TaskMessage.TYPE:
                writeTask(new TaskMessage(bytes).getTask());
                break;
            case VerdictMessage.TYPE:
                writeVerdict(new VerdictMessage(bytes).getVerdict());
                break;
        }
    }

    private static Path buildTaskFilePath(String name, long deadline, Signature signature) {
        return Paths.get(String.format(Configuration.TASK_FILE_FORMAT, Configuration.SERVER_FILES_PATH, UsersContainer.getInstance().getLogin(signature), deadline, name));
    }

    private void writeTask(Task task) {
        writeFile(buildTaskFilePath(task.getName(), task.getDeadline(), task.getSignature()), task.getText().getBytes(Configuration.UTF8_CHARSET));
    }

    private void writeVerdict(Verdict verdict) {

    }
}
