package coursework.client;

import coursework.client.gui.LecturerClientFrame;
import coursework.client.runnables.LecturersClientRunnable;
import coursework.common.Utils;
import coursework.common.model.Task;
import coursework.common.model.Verdict;

/**
 * @author adkozlov
 */
public class LecturerClient extends Client {

    public static void main(String[] args) {
        Utils.startRunnable(new LecturerClient());
    }

    @Override
    public void run() {
        new LecturerClientFrame(this);
    }

    public void newTask(String name, String text, long deadline) {
        startClientRunnable(new LecturersClientRunnable(new Task(name, text, deadline, getSignature())));
    }

    public void newVerdict(String name, boolean accepted, String comments) {
        startClientRunnable(new LecturersClientRunnable(new Verdict(name, accepted, comments, getSignature())));
    }
}
