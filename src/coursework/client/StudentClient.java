package coursework.client;

import coursework.client.gui.StudentClientFrame;
import coursework.client.runnables.StudentsClientRunnable;
import coursework.common.FileWrapper;
import coursework.common.model.Solution;

/**
 * @author adkozlov
 */
public class StudentClient extends Client {

    public static void main(String[] args) {
        new StudentClient().start();
    }

    @Override
    public void run() {
        super.run();
        new StudentClientFrame(this);
    }

    public void newSolution(String courseName, String name, FileWrapper solution) {
        new StudentsClientRunnable(new Solution(courseName, name, solution, getSignature())).start();
    }

    @Override
    public boolean isStudentsObject() {
        return true;
    }
}
