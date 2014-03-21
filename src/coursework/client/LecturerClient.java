package coursework.client;

import coursework.client.gui.LecturerClientFrame;
import coursework.client.runnables.LecturersClientRunnable;
import coursework.common.Configuration;
import coursework.common.model.Task;
import coursework.common.model.Verdict;

import java.net.InetAddress;

/**
 * @author adkozlov
 */
public class LecturerClient extends Client {

    public LecturerClient(InetAddress serverAddress) {
        super(serverAddress);
    }

    @Override
    public void run() {
        super.run();
        new LecturerClientFrame(this);
    }

    public void newTask(String courseName, String taskName, String text, long deadline) {
        new LecturersClientRunnable(getServerAddress(), new Task(courseName, taskName, text, deadline, getSignature())).start();
    }

    public void newVerdict(String studentName, String taskName, boolean accepted, String comments) {
        new LecturersClientRunnable(getServerAddress(), new Verdict(studentName, taskName, accepted, comments, getSignature())).start();
    }

    @Override
    public boolean isStudentsObject() {
        return false;
    }

    @Override
    public String getStoragePath() {
        return Configuration.LECTURER_FILES_PATH;
    }
}
