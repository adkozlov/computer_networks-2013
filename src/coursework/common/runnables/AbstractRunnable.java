package coursework.common.runnables;

import coursework.common.Configuration;
import coursework.common.messages.IMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;

/**
 * @author adkozlov
 */
public abstract class AbstractRunnable implements Runnable {

    protected abstract int getPort();

    protected abstract void readAndWrite(Socket socket) throws IOException;

    protected final byte[] readBytes(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();

        byte[] buffer = new byte[Configuration.BUFFER_LENGTH];
        inputStream.read(buffer);

        return buffer;
    }

    protected final void writeMessage(Socket socket, IMessage message) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(message.toByteArray());
    }

    protected final void writeMessages(Socket socket, Collection<IMessage> messages) throws IOException {
        OutputStream outputStream = socket.getOutputStream();

        for (IMessage message : messages) {
            outputStream.write(message.toByteArray());
        }
    }
}
