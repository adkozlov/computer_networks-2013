package coursework.common.messages;

import coursework.common.model.AuthenticationRequest;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class AuthenticationRequestMessage extends AbstractMessage {

    private final AuthenticationRequest authenticationRequest;

    public AuthenticationRequestMessage(byte[] bytes) throws IOException {
        super(bytes);
        authenticationRequest = new AuthenticationRequest(dataInputStream.readInt(), dataInputStream.readInt());
    }

    @Override
    protected void writeMessage(DataOutputStream dataOutputStream) throws IOException {
        super.writeMessage(dataOutputStream);
        dataOutputStream.writeInt(authenticationRequest.getLoginHashCode());
        dataOutputStream.writeInt(authenticationRequest.getPasswordHashCode());
    }

    @Override
    public byte getType() {
        return 0x00;
    }
}
