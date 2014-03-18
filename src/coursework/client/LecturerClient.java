package coursework.client;

import coursework.client.gui.LecturerClientFrame;
import coursework.client.runnables.LecturersClientRunnable;
import coursework.common.Utils;
import coursework.common.model.Task;

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
        LecturersClientRunnable lecturersClientRunnable = new LecturersClientRunnable(new Task(name, text, deadline, getSignature()));

        Utils.startRunnable(lecturersClientRunnable);
    }
}
