package coursework.common.messages;

import coursework.common.model.SolutionResponse;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class SolutionResponseMessage extends SignedObjectMessage {

    private final SolutionResponse solutionResponse;

    public SolutionResponseMessage(byte[] bytes) throws IOException {
        super(bytes);
        solutionResponse = new SolutionResponse(dataInputStream.readBoolean(), readString(dataInputStream), getSignature());
    }

    public SolutionResponseMessage(SolutionResponse solutionResponse) {
        super(solutionResponse.getSignature());
        this.solutionResponse = solutionResponse;
    }

    @Override
    protected void writeMessage(DataOutputStream dataOutputStream) throws IOException {
        super.writeMessage(dataOutputStream);
        dataOutputStream.writeBoolean(solutionResponse.isAccepted());
        writeString(dataOutputStream, solutionResponse.getComments());
    }

    public static final byte TYPE = 0x04;

    static {
        type = TYPE;
    }
}
