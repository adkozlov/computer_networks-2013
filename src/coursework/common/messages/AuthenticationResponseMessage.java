package coursework.common.messages;

import coursework.common.model.AuthenticationResponse;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class AuthenticationResponseMessage extends AbstractMessage {

    private final AuthenticationResponse authenticationResponse;

    public AuthenticationResponseMessage(AuthenticationResponse authenticationResponse) {
        this.authenticationResponse = authenticationResponse;
    }

    public AuthenticationResponseMessage(byte[] bytes) throws IOException {
        super(bytes);
        authenticationResponse = dataInputStream.readBoolean() ? new AuthenticationResponse(readString(dataInputStream)) : new AuthenticationResponse();
    }

    public AuthenticationResponse getAuthenticationResponse() {
        return authenticationResponse;
    }

    @Override
    protected void writeMessage(DataOutputStream dataOutputStream) throws IOException {
        super.writeMessage(dataOutputStream);
        dataOutputStream.writeBoolean(authenticationResponse.isPassed());
        writeString(dataOutputStream, authenticationResponse.getName());
    }

    @Override
    public byte getType() {
        return 0x01;
    }
}
