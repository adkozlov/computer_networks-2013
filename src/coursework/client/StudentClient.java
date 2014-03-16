package coursework.client;

import coursework.client.gui.StudentClientFrame;
import coursework.common.Utils;

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
}
