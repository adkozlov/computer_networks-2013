package coursework.common.messages;

import coursework.common.Signature;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public abstract class SignedObjectMessage extends AbstractMessage {

    private final Signature signature;

    protected SignedObjectMessage(byte[] bytes) throws IOException {
        super(bytes);
        signature = new Signature(readBytes(dataInputStream));
    }

    protected SignedObjectMessage(Signature signature) {
        this.signature = signature;
    }

    public Signature getSignature() {
        return signature;
    }

    @Override
    protected void writeMessage(DataOutputStream dataOutputStream) throws IOException {
        super.writeMessage(dataOutputStream);
        writeBytes(dataOutputStream, signature.getBytes());
    }
}
