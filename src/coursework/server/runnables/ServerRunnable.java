package coursework.server.runnables;

import coursework.client.IGroupable;
import coursework.common.Configuration;
import coursework.common.Logger;
import coursework.common.runnables.AbstractRunnable;
import coursework.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author adkozlov
 */
public abstract class ServerRunnable extends AbstractRunnable implements IGroupable {

    private final Server server;

    public ServerRunnable(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

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

    @Override
    protected String getFilePath() {
        return Configuration.SERVER_FILES_PATH;
    }
}
