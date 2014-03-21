package coursework.common.messages;

import coursework.common.model.Verdict;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class VerdictMessage extends SignedObjectMessage {

    private final Verdict verdict;

    public VerdictMessage(byte[] bytes) throws IOException {
        super(bytes);
        verdict = new Verdict(readString(dataInputStream), readString(dataInputStream), dataInputStream.readBoolean(), readString(dataInputStream), getSignature());
    }

    public VerdictMessage(Verdict verdict) {
        super(verdict.getSignature());
        this.verdict = verdict;
    }

    protected static void writeVerdict(DataOutputStream dataOutputStream, Verdict verdict) throws IOException {
        writeString(dataOutputStream, verdict.getStudentName());
        writeString(dataOutputStream, verdict.getTaskName());
        dataOutputStream.writeBoolean(verdict.isAccepted());
        writeString(dataOutputStream, verdict.getComments());
    }

    @Override
    protected void writeMessage(DataOutputStream dataOutputStream) throws IOException {
        super.writeMessage(dataOutputStream);
        writeVerdict(dataOutputStream, verdict);
    }

    public static final byte TYPE = 0x04;

    static {
        type = TYPE;
    }

    public Verdict getVerdict() {
        return verdict;
    }
}
