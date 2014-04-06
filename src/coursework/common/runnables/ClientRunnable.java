package coursework.common.runnables;

import coursework.client.IGroupable;
import coursework.common.Logger;

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

    protected ClientRunnable(InetAddress address) {
        this.address = address;
        port = 0;
    }

    @Override
    public void run() {
        Socket socket = null;

        try {
            socket = new Socket(address, port == 0 ? getPort() : port);

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
