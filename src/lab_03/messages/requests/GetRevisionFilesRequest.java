package lab_03.messages.requests;

import lab_03.utils.Key;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class GetRevisionFilesRequest extends Request {

    private static final byte CODE = 0x02;

    private final Key publicKey;

    protected GetRevisionFilesRequest(Key publicKey) {
        super(CODE);
        this.publicKey = publicKey;
    }

    public static GetRevisionFilesRequest readFromStream(DataInputStream dis) throws IOException {
        assertCodeFromStream(dis, CODE);
        return new GetRevisionFilesRequest(Key.readFromStream(dis));
    }

    @Override
    public void writeToStream(DataOutputStream dos) throws IOException {
        super.writeToStream(dos);

        publicKey.writeToStream(dos);
    }
}
