package lab_03.messages.requests;

import lab_03.utils.SHA256Hash;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class GetFileRequest extends Request {

    private static final byte CODE = 0x03;

    private final SHA256Hash hash;

    public GetFileRequest(SHA256Hash hash) {
        super(CODE);
        this.hash = hash;
    }

    public static GetFileRequest readFromStream(DataInputStream dis) throws IOException {
        assertCodeFromStream(dis, CODE);
        return new GetFileRequest(SHA256Hash.readFromStream(dis));
    }

    @Override
    public void writeToStream(DataOutputStream dos) throws IOException {
        super.writeToStream(dos);

        hash.writeToStream(dos);
    }
}
