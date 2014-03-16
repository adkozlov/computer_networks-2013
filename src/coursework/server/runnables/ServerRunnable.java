package coursework.server.runnables;

import coursework.common.Logger;
import coursework.common.runnables.AbstractRunnable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author adkozlov
 */
public abstract class ServerRunnable extends AbstractRunnable {

    @Override
    public void run() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(getPort());

            while (true) {
                Socket socket = serverSocket.accept();

                readAndWrite(socket);

                socket.close();
            }
        } catch (IOException e) {
            Logger.getInstance().logException(e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    Logger.getInstance().logException(e);
                }
            }
        }
    }
}
