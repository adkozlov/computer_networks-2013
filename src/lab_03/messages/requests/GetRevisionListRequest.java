package lab_03.messages.requests;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class GetRevisionListRequest extends Request {

    private static final byte CODE = 0x01;

    public GetRevisionListRequest() {
        super(CODE);
    }

    public static GetRevisionListRequest readFromStream(DataInputStream dis) throws IOException {
        assertCodeFromStream(dis, CODE);
        return new GetRevisionListRequest();
    }
}
