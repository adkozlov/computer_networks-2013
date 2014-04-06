package coursework.common.runnables;

import coursework.common.Configuration;
import coursework.common.Logger;
import coursework.common.messages.IMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author adkozlov
 */
public abstract class AbstractRunnable extends Thread {

    protected abstract int getPort();

    protected abstract void readAndWrite(Socket socket) throws IOException;

    protected final byte[] readBytes(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();

        byte[] buffer = new byte[Configuration.BUFFER_LENGTH];
        inputStream.read(buffer);

        return buffer;
    }

    protected abstract IMessage readMessage(byte[] bytes) throws IOException;

    protected final void writeMessage(Socket socket, IMessage message) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(message.toByteArray());
    }

    protected void writeFile(Path path, byte[] bytes) {
        try {
            Path parent = path.getParent();
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            Files.write(path, bytes, StandardOpenOption.CREATE);
        } catch (IOException e) {
            Logger.getInstance().logException(e);
        }
    }
}
