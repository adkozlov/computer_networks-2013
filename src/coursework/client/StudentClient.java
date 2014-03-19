package coursework.client;

import coursework.client.gui.StudentClientFrame;
import coursework.client.runnables.StudentsClientRunnable;
import coursework.common.FileWrapper;
import coursework.common.Utils;
import coursework.common.model.Solution;

/**
 * @author adkozlov
 */
public class StudentClient extends Client {

    public static void main(String[] args) {
        Utils.startRunnable(new StudentClient());
    }

    @Override
    public void run() {
        new StudentClientFrame(this);
    }

    public void newSolution(String courseName, String name, FileWrapper solution) {
        startClientRunnable(new StudentsClientRunnable(new Solution(courseName, name, solution, getSignature())));
    }
}
