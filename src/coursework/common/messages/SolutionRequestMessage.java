package coursework.common.messages;

import coursework.common.model.SolutionRequest;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class SolutionRequestMessage extends AbstractMessage {

    private final SolutionRequest solutionRequest;

    public SolutionRequestMessage(byte[] bytes) throws IOException {
        super(bytes);
        solutionRequest = new SolutionRequest(readFile(dataInputStream));
    }

    @Override
    protected void writeMessage(DataOutputStream dataOutputStream) throws IOException {
        super.writeMessage(dataOutputStream);
        writeFileWrapper(dataOutputStream, solutionRequest.getFileWrapper());
    }

    @Override
    public int getType() {
        return 0x02;
    }
}
