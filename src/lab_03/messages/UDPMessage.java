package lab_03.messages;

import lab_03.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class UDPMessage extends Message {

    public static UDPMessage fromByteArray(byte[] bytes) throws IOException {
        return readFromStream(createInputStream(bytes));
    }

    private static UDPMessage readFromStream(DataInputStream dataInputStream) {
        return null;
    }

    @Override
    protected void writeToStream(DataOutputStream dos) {

    }


}
