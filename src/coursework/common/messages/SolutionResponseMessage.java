package coursework.common.messages;

import coursework.common.model.SolutionResponse;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class SolutionResponseMessage extends AbstractMessage {

    private final SolutionResponse solutionResponse;

    public SolutionResponseMessage(byte[] bytes) throws IOException {
        super(bytes);
        solutionResponse = new SolutionResponse(dataInputStream.readBoolean(), readString(dataInputStream));
    }

    @Override
    protected void writeMessage(DataOutputStream dataOutputStream) throws IOException {
        super.writeMessage(dataOutputStream);
        dataOutputStream.writeBoolean(solutionResponse.isAccepted());
        writeString(dataOutputStream, solutionResponse.getComments());
    }

    @Override
    public int getType() {
        return 0x03;
    }
}
