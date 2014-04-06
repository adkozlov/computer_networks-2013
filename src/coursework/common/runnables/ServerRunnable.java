package coursework.common.runnables;

import coursework.client.IGroupable;
import coursework.common.*;
import coursework.common.model.Solution;
import coursework.common.model.Task;
import coursework.common.model.Verdict;
import coursework.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;

/**
 * @author adkozlov
 */
public abstract class ServerRunnable extends AbstractRunnable implements IGroupable {

    private final Server server;

    public ServerRunnable(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;

        while (true) {
            try {
                serverSocket = new ServerSocket(getPort());

                while (true) {
                    Socket socket = serverSocket.accept();

                    readAndWrite(socket);

                    socket.close();
                }
            } catch (IOException e) {
                Logger.getInstance().logException(e);
            } finally {
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        Logger.getInstance().logException(e);
                    }
                }
            }
        }
    }

    protected abstract String getFilePath(Signature signature);

    protected abstract Path buildTaskFilePath(String name, long deadline, Signature signature);

    protected void writeTask(Task task) {
        if (!UsersContainer.getInstance().isStudent(task.getSignature())) {
            writeFile(buildTaskFilePath(task.getTaskName(), task.getDeadline(), task.getSignature()), Utils.getBytes(task.getText()));
        }
    }

    protected abstract Path buildVerdictFilePath(String studentName, String taskName, boolean accepted, Signature signature);

    protected void writeVerdict(Verdict verdict) {
        if (UsersContainer.getInstance().isStudent(verdict.getStudentName())) {
            writeFile(buildVerdictFilePath(verdict.getStudentName(), verdict.getTaskName(), verdict.isAccepted(), verdict.getSignature()), Utils.getBytes(verdict.getComments()));
        }
    }

    protected abstract Path buildSolutionFilePath(String fileName, Signature signature, String taskName, String courseName);

    protected void writeSolution(Solution solution) {
        if (!UsersContainer.getInstance().isLecturer(solution.getSignature())) {
            FileWrapper fileWrapper = solution.getFileWrapper();
            Path path = buildSolutionFilePath(fileWrapper.getFileName(), solution.getSignature(), solution.getTaskName(), solution.getCourseName());

            writeFile(path, fileWrapper.getContent());
        }
    }
}
