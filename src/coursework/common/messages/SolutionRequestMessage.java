package coursework.common.messages;

import coursework.common.model.SolutionRequest;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class SolutionRequestMessage extends SignedObjectMessage {

    private final SolutionRequest solutionRequest;

    public SolutionRequestMessage(byte[] bytes) throws IOException {
        super(bytes);
        solutionRequest = new SolutionRequest(readFile(dataInputStream), getSignature());
    }

    public SolutionRequestMessage(SolutionRequest solutionRequest) {
        super(solutionRequest.getSignature());
        this.solutionRequest = solutionRequest;
    }

    @Override
    protected void writeMessage(DataOutputStream dataOutputStream) throws IOException {
        super.writeMessage(dataOutputStream);
        writeFileWrapper(dataOutputStream, solutionRequest.getFileWrapper());
    }

    public static final byte TYPE = 0x03;

    static {
        type = TYPE;
    }

    public SolutionRequest getSolutionRequest() {
        return solutionRequest;
    }
}
