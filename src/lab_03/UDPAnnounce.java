package lab_03;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class UDPAnnounce extends Message {

    public static Message fromByteArray(byte[] bytes) throws IOException {
        return readFromStream(createInputStream(bytes));
    }

    private static UDPAnnounce readFromStream(DataInputStream dataInputStream) {
        return null;
    }

    @Override
    protected void writeToStream(DataOutputStream dos) {

    }


}
