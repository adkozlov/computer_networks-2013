package coursework.common.messages;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class AuthenticationResponseMessage extends AbstractMessage {

    private final boolean passed;

    public AuthenticationResponseMessage(byte[] bytes) throws IOException {
        super(bytes);
        passed = dataInputStream.readBoolean();
    }

    @Override
    protected void writeMessage(DataOutputStream dataOutputStream) throws IOException {
        super.writeMessage(dataOutputStream);
        dataOutputStream.writeBoolean(passed);
    }

    @Override
    public byte getType() {
        return 0x01;
    }
}
