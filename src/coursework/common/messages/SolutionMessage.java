package coursework.common.messages;

import coursework.common.model.Solution;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class SolutionMessage extends SignedObjectMessage {

    private final Solution solution;

    public SolutionMessage(byte[] bytes) throws IOException {
        super(bytes);
        solution = new Solution(readString(dataInputStream), readString(dataInputStream), readFile(dataInputStream), getSignature());
    }

    public SolutionMessage(Solution solution) {
        super(solution.getSignature());
        this.solution = solution;
    }

    @Override
    protected void writeMessage(DataOutputStream dataOutputStream) throws IOException {
        super.writeMessage(dataOutputStream);
        writeString(dataOutputStream, solution.getCourseName());
        writeString(dataOutputStream, solution.getTaskName());
        writeFileWrapper(dataOutputStream, solution.getFileWrapper());
    }

    public static final byte TYPE = 0x03;

    static {
        type = TYPE;
    }

    public Solution getSolution() {
        return solution;
    }
}
