package coursework.client;

import coursework.client.gui.LecturerClientFrame;
import coursework.client.runnables.LecturersClientRunnable;
import coursework.common.model.Task;
import coursework.common.model.Verdict;

/**
 * @author adkozlov
 */
public class LecturerClient extends Client {

    public static void main(String[] args) {
        new LecturerClient().start();
    }

    @Override
    public void run() {
        new LecturerClientFrame(this);
    }

    public void newTask(String courseName, String taskName, String text, long deadline) {
        new LecturersClientRunnable(new Task(courseName, taskName, text, deadline, getSignature())).start();
    }

    public void newVerdict(String studentName, String taskName, boolean accepted, String comments) {
        new LecturersClientRunnable(new Verdict(studentName, taskName, accepted, comments, getSignature())).start();
    }

    @Override
    public boolean isStudentsObject() {
        return false;
    }
}
