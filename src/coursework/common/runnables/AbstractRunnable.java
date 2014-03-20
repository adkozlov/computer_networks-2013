package coursework.common.runnables;

import coursework.common.*;
import coursework.common.messages.IMessage;
import coursework.common.model.Solution;
import coursework.common.model.Task;
import coursework.common.model.Verdict;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author adkozlov
 */
public abstract class AbstractRunnable implements Runnable {

    protected abstract int getPort();

    protected abstract void readAndWrite(Socket socket) throws IOException;

    protected final byte[] readBytes(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();

        byte[] buffer = new byte[Configuration.BUFFER_LENGTH];
        inputStream.read(buffer);

        return buffer;
    }

    protected abstract IMessage readMessage(byte[] bytes) throws IOException;

    protected final List<IMessage> readMessages(Socket socket) throws IOException {
        byte[] bytes = readBytes(socket);

        List<IMessage> result = new ArrayList<>();

        int from = 0;
        while (from < bytes.length) {
            int length = ByteBuffer.wrap(bytes, from, Configuration.INT_BYTES_LENGTH).getInt();

            result.add(readMessage(Arrays.copyOfRange(bytes, from, length)));

            from += length;
        }

        return result;
    }

    protected final void writeMessage(Socket socket, IMessage message) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(message.toByteArray());
    }

    protected void writeFile(Path path, byte[] bytes) {
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

    protected abstract String getFilePath();

    protected Path buildTaskFilePath(String name, long deadline, Signature signature) {
        return Paths.get(String.format(Configuration.TASK_FILE_FORMAT, getFilePath(), UsersContainer.getInstance().getLogin(signature), Utils.longToDateString(deadline), name));
    }

    protected void writeTask(Task task) {
        writeFile(buildTaskFilePath(task.getName(), task.getDeadline(), task.getSignature()), Utils.getBytes(task.getText()));
    }

    protected Path buildVerdictFilePath(String studentName, String taskName, boolean accepted, Signature signature) {
        return Paths.get(String.format(Configuration.VERDICT_FILE_FORMAT, getFilePath(), UsersContainer.getInstance().getLogin(signature), taskName, studentName, Utils.nowToDateString(), accepted));
    }

    protected void writeVerdict(Verdict verdict) {
        writeFile(buildVerdictFilePath(verdict.getStudentName(), verdict.getTaskName(), verdict.isAccepted(), verdict.getSignature()), Utils.getBytes(verdict.getComments()));
    }

    protected Path buildSolutionFilePath(String fileName, Signature signature, String taskName, String courseName) {
        return Paths.get(String.format(Configuration.SOLUTION_FILE_FORMAT, getFilePath(), courseName, taskName, UsersContainer.getInstance().getLogin(signature), Utils.nowToDateString(), fileName));
    }

    protected void writeSolution(Solution solution) {
        FileWrapper fileWrapper = solution.getFileWrapper();
        Path path = buildSolutionFilePath(fileWrapper.getFileName(), solution.getSignature(), solution.getTaskName(), solution.getCourseName());

        writeFile(path, fileWrapper.getContent());
    }
}
