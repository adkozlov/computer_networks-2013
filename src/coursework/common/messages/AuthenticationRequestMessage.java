package coursework.common.messages;

import coursework.common.model.AuthenticationRequest;
import coursework.common.model.LecturerAuthenticationRequest;
import coursework.common.model.StudentAuthenticationRequest;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class AuthenticationRequestMessage extends AbstractMessage {

    private final AuthenticationRequest authenticationRequest;

    public AuthenticationRequestMessage(AuthenticationRequest authenticationRequest) {
        this.authenticationRequest = authenticationRequest;
    }

    public AuthenticationRequestMessage(byte[] bytes) throws IOException {
        super(bytes);

        boolean passed = dataInputStream.readBoolean();
        String login = readString(dataInputStream);
        int passwordHashCode = dataInputStream.readInt();
        authenticationRequest = passed ? new StudentAuthenticationRequest(login, passwordHashCode) : new LecturerAuthenticationRequest(login, passwordHashCode);
    }

    @Override
    protected void writeMessage(DataOutputStream dataOutputStream) throws IOException {
        super.writeMessage(dataOutputStream);

        dataOutputStream.writeBoolean(authenticationRequest.isStudent());
        writeString(dataOutputStream, authenticationRequest.getLogin());
        dataOutputStream.writeInt(authenticationRequest.getPasswordHashCode());
    }

    @Override
    public int getType() {
        return 0x00;
    }

    public AuthenticationRequest getAuthenticationRequest() {
        return authenticationRequest;
    }
}
