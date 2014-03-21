package coursework.client;

import coursework.client.gui.StudentClientFrame;
import coursework.client.runnables.StudentsClientRunnable;
import coursework.common.Configuration;
import coursework.common.FileWrapper;
import coursework.common.model.Solution;

import java.net.InetAddress;

/**
 * @author adkozlov
 */
public class StudentClient extends Client {

    public StudentClient(InetAddress serverAddress) {
        super(serverAddress);
    }

    @Override
    public void run() {
        super.run();
        new StudentClientFrame(this);
    }

    public void newSolution(String courseName, String name, FileWrapper solution) {
        new StudentsClientRunnable(getServerAddress(), new Solution(courseName, name, solution, getSignature())).start();
    }

    @Override
    public boolean isStudentsObject() {
        return true;
    }

    @Override
    public String getStoragePath() {
        return Configuration.STUDENT_FILES_PATH;
    }
}
