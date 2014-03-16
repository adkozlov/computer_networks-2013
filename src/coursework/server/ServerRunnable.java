package coursework.server;

import coursework.common.Configuration;
import coursework.common.Logger;
import coursework.common.messages.IMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;

/**
 * @author adkozlov
 */
public abstract class ServerRunnable implements Runnable {

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

    protected abstract int getPort();

    protected abstract void readAndWrite(Socket socket) throws IOException;

    protected final byte[] readBytes(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();

        byte[] buffer = new byte[Configuration.BUFFER_LENGTH];
        inputStream.read(buffer);

        return buffer;
    }

    protected final void writeMessages(Socket socket, Collection<IMessage> messages) throws IOException {
        OutputStream outputStream = socket.getOutputStream();

        for (IMessage message : messages) {
            outputStream.write(message.toByteArray());
        }
    }
}
