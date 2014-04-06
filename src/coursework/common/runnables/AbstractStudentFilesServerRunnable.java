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
public abstract class AbstractStudentFilesServerRunnable extends ServerRunnable {

    public AbstractStudentFilesServerRunnable(Server server) {
        super(server);
    }

    @Override
    protected Path buildTaskFilePath(String name, long deadline, Signature signature) {
        return Paths.get(String.format(Configuration.TASK_FILE_FORMAT, getFilePath(), UsersContainer.getInstance().getLogin(signature), Utils.longToDateString(deadline), name));
    }

    @Override
    protected Path buildVerdictFilePath(String studentName, String taskName, boolean accepted, Signature signature) {
        return Paths.get(String.format(Configuration.VERDICT_FILE_FORMAT, getFilePath(), UsersContainer.getInstance().getLogin(signature), taskName, studentName, Utils.nowToDateString(), accepted));
    }

    @Override
    protected Path buildSolutionFilePath(String fileName, Signature signature, String taskName, String courseName) {
        throw new UnsupportedOperationException("Solution file path cannot be built by lecturer server");
    }
}
