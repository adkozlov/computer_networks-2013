package coursework.server.runnables;

import coursework.common.*;
import coursework.common.messages.SolutionRequestMessage;
import coursework.common.messages.TaskMessage;
import coursework.common.model.SolutionRequest;
import coursework.common.model.Task;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
            case SolutionRequestMessage.TYPE:
                writeSolutionRequest(new SolutionRequestMessage(bytes).getSolutionRequest());
                break;
        }
    }

    private Path buildFilePath(String fileName, long time, Signature signature, String objectFolderName, String extension) {
        return Paths.get(String.format(Configuration.FILE_FORMAT, Configuration.SERVER_FILES_PATH, objectFolderName, UsersContainer.getInstance().getLogin(signature), time, fileName, extension));
    }

    private void writeTask(Task task) {
        Path path = buildFilePath(task.getName(), task.getDeadline(), task.getSignature(), Configuration.TASKS_FOLDER, Configuration.TASK_EXTENSION);

        writeFile(path, task.getText().getBytes(Configuration.UTF8_CHARSET));
    }

    private void writeSolutionRequest(SolutionRequest solutionRequest) {
        FileWrapper fileWrapper = solutionRequest.getFileWrapper();
        Path path = buildFilePath(fileWrapper.getFileName(), System.currentTimeMillis(), solutionRequest.getSignature(), Configuration.SOLUTIONS_FOLDER, Configuration.SOLUTION_EXTENSION);

        writeFile(path, fileWrapper.getContent());
    }

    private void writeFile(Path path, byte[] bytes) {
        try {
            Path parent = path.getParent();
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            Files.write(path, bytes, StandardOpenOption.CREATE);
        } catch (IOException e) {
            Logger.getInstance().logException(e);
        }
    }
}
