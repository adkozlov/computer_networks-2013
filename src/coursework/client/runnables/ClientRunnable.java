package coursework.client.runnables;

import coursework.client.IGroupable;
import coursework.common.Configuration;
import coursework.common.Logger;
import coursework.common.runnables.AbstractRunnable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author adkozlov
 */
public abstract class ClientRunnable extends AbstractRunnable implements IGroupable {

    private final InetAddress address;
    private final int port;

    protected ClientRunnable(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    protected ClientRunnable() {
        address = null;
        port = 0;
    }

    @Override
    public void run() {
        Socket socket = null;

        try {
            if (address == null) {
                socket = new Socket(Configuration.SERVER_IP, getPort());
            } else {
                socket = new Socket(address, port);
            }

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
