package lab_03.messages;

import lab_03.utils.Key;
import lab_03.utils.KeyGenerator;
import lab_03.utils.SHA256Hash;
import lab_03.utils.SHA256Hasher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class UDPMessage extends Message {

    private final Key publicKey;
    private final SHA256Hash revisionListHash;

    public UDPMessage() {
        publicKey = KeyGenerator.getInstance().getPublicKey();
        revisionListHash = SHA256Hasher.revisionListHash();
    }

    public UDPMessage(Key publicKey, SHA256Hash revisionListHash) {
        this.publicKey = publicKey;
        this.revisionListHash = revisionListHash;
    }

    public static UDPMessage readFromStream(DataInputStream dis) throws IOException {
        return new UDPMessage(Key.readFromStream(dis), SHA256Hash.readFromStream(dis));
    }

    public static UDPMessage fromByteArray(byte[] bytes) throws IOException {
        return readFromStream(createInputStream(bytes));
    }

    @Override
    public void writeToStream(DataOutputStream dos) throws IOException {
        publicKey.writeToStream(dos);
        revisionListHash.writeToStream(dos);
    }
}
