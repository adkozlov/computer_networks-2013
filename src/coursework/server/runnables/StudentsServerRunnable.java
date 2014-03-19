package coursework.server.runnables;

import coursework.common.Configuration;
import coursework.common.FileWrapper;
import coursework.common.Signature;
import coursework.common.UsersContainer;
import coursework.common.messages.SolutionMessage;
import coursework.common.model.Solution;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author adkozlov
 */
public class StudentsServerRunnable extends ServerRunnable {
    @Override
    protected int getPort() {
        return Configuration.STUDENTS_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        writeSolution(new SolutionMessage(readBytes(socket)).getSolution());
    }

    private static Path buildSolutionFilePath(String fileName, Signature signature, String taskName, String courseName) {
        return Paths.get(String.format(Configuration.SOLUTION_FILE_FORMAT, Configuration.SERVER_FILES_PATH, courseName, taskName, UsersContainer.getInstance().getLogin(signature), System.currentTimeMillis(), fileName));
    }

    private void writeSolution(Solution solution) {
        FileWrapper fileWrapper = solution.getFileWrapper();
        Path path = buildSolutionFilePath(fileWrapper.getFileName(), solution.getSignature(), solution.getTaskName(), solution.getCourseName());

        writeFile(path, fileWrapper.getContent());
    }
}