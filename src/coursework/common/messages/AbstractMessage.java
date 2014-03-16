package coursework.common.messages;

import coursework.common.FileWrapper;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author adkozlov
 */
public abstract class AbstractMessage implements IMessage {

    @Override
    public final byte[] toByteArray() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        writeMessage(dataOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public abstract int getType();

    protected final DataInputStream dataInputStream;

    protected AbstractMessage() {
        dataInputStream = null;
    }

    protected AbstractMessage(byte[] bytes) throws IOException {
        dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));

        int type = dataInputStream.readInt();
        if (type != getType()) {
            throw new MessageTypeRecognizingException(type);
        }
    }

    private static class MessageTypeRecognizingException extends IOException {

        public MessageTypeRecognizingException(int i) {
            super("There is no such type of message: " + i);
        }
    }

    protected void writeMessage(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(getType());
    }

    public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    private static byte[] readBytes(DataInputStream dataInputStream) throws IOException {
        int length = dataInputStream.readInt();
        byte[] bytes = new byte[length];
        dataInputStream.read(bytes);

        return bytes;
    }

    private static void writeBytes(DataOutputStream dataOutputStream, byte[] bytes) throws IOException {
        dataOutputStream.writeInt(bytes.length);
        dataOutputStream.write(bytes);
    }

    protected static String readString(DataInputStream dataInputStream) throws IOException {
        return new String(readBytes(dataInputStream), UTF8_CHARSET);
    }

    protected static void writeString(DataOutputStream dataOutputStream, String s) throws IOException {
        writeBytes(dataOutputStream, s.getBytes(UTF8_CHARSET));
    }

    protected static FileWrapper readFile(DataInputStream dataInputStream) throws IOException {
        return new FileWrapper(readString(dataInputStream), readBytes(dataInputStream));
    }

    protected static void writeFileWrapper(DataOutputStream dataOutputStream, FileWrapper fileWrapper) throws IOException {
        writeString(dataOutputStream, fileWrapper.getFileName());
        writeBytes(dataOutputStream, fileWrapper.getContent());
    }
}
