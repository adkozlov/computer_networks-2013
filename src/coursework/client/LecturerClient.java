package coursework.client;

import coursework.client.gui.LecturerClientFrame;
import coursework.common.Utils;

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
}
