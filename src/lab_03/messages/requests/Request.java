package lab_03.messages.requests;

import lab_03.messages.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public abstract class Request extends Message {

    protected final byte CODE;

    protected Request(byte code) {
        CODE = code;
    }

    protected static void assertCodeFromStream(DataInputStream dis, byte code) throws IOException {
        byte result = dis.readByte();
        assert result == code;
    }

    @Override
    public void writeToStream(DataOutputStream dos) throws IOException {
        dos.writeByte(CODE);
    }
}
