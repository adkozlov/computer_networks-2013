package coursework.common.runnables;

import coursework.common.Configuration;
import coursework.common.Signature;
import coursework.common.UsersContainer;
import coursework.common.Utils;
import coursework.server.Server;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author adkozlov
 */
public abstract class AbstractLecturerFilesServerRunnable extends ServerRunnable {

    public AbstractLecturerFilesServerRunnable(Server server) {
        super(server);
    }

    @Override
    protected Path buildTaskFilePath(String name, long deadline, Signature signature) {
        throw new UnsupportedOperationException("Task file path cannot be built by lecturer server");
    }

    @Override
    protected Path buildVerdictFilePath(String studentName, String taskName, boolean accepted, Signature signature) {
        throw new UnsupportedOperationException("Verdict file path cannot be built by lecturer server");
    }

    @Override
    protected Path buildSolutionFilePath(String fileName, Signature signature, String taskName, String courseName) {
        return Paths.get(String.format(Configuration.SOLUTION_FILE_FORMAT, getFilePath(), courseName, taskName, UsersContainer.getInstance().getLogin(signature), Utils.nowToDateString(), fileName));
    }
}
