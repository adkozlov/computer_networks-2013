package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.Logger;
import coursework.common.runnables.AbstractRunnable;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public abstract class ClientRunnable extends AbstractRunnable {

    @Override
    public void run() {
        Socket socket = null;

        try {
            socket = new Socket(Configuration.SERVER_IP, getPort());

            readAndWrite(socket);
        } catch (IOException e) {
            Logger.getInstance().logException(e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    Logger.getInstance().logException(e);
                }
            }
        }
    }
}
