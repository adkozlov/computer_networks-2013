package lab_03.messages.responses;

import lab_03.utils.Key;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author adkozlov
 */
public class GetFileResponse extends Response {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private final boolean isOk;
    private final Key publicKey;
    private final String name;
    private final String content;

    public boolean isOk() {
        return isOk;
    }

    public GetFileResponse() {
        isOk = false;

        publicKey = null;
        name = null;
        content = null;
    }

    public GetFileResponse(Key publicKey, String name, String content) {
        isOk = true;

        this.publicKey = publicKey;
        this.name = name;
        this.content = content;
    }

    public static GetFileResponse readFromStream(DataInputStream dis) throws IOException {
        byte code = dis.readByte();

        switch (code) {
            case OK_CODE:
                Key publicKey = Key.readFromStream(dis);

                int nameLength = dis.readInt();
                int contentLength = dis.readInt();

                String name = readStringFromStream(dis, nameLength);
                String content = readStringFromStream(dis, contentLength);

                return new GetFileResponse(publicKey, name, content);
            case DENIED_CODE:
                return new GetFileResponse();
            default:
                throw new IOException(String.format(EXCEPTION_FORMAT, code));
        }
    }

    private static String readStringFromStream(DataInputStream dis, int length) throws IOException {
        byte[] result = new byte[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = dis.readByte();
        }

        return new String(result, DEFAULT_CHARSET);
    }

    private static void writeStringToStream(DataOutputStream dos, String s) throws IOException {
        byte[] bytes = s.getBytes(DEFAULT_CHARSET);

        for (byte b : bytes) {
            dos.writeByte(b);
        }
    }

    @Override
    public void writeToStream(DataOutputStream dos) throws IOException {
        if (isOk()) {
            dos.writeByte(OK_CODE);

            publicKey.writeToStream(dos);

            dos.writeInt(name.length());
            dos.writeInt(content.length());

            writeStringToStream(dos, name);
            writeStringToStream(dos, content);
        } else {
            dos.writeByte(DENIED_CODE);
        }
    }
}
