package coursework.common.messages;

import coursework.common.model.Task;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class TaskMessage extends AbstractMessage {

    private final Task task;

    public TaskMessage(byte[] bytes) throws IOException {
        super(bytes);
        task = new Task(readString(dataInputStream), dataInputStream.readLong());
    }

    @Override
    protected void writeMessage(DataOutputStream dataOutputStream) throws IOException {
        super.writeMessage(dataOutputStream);
        writeString(dataOutputStream, task.getText());
        dataOutputStream.writeLong(task.getDeadline());
    }

    @Override
    public byte getType() {
        return 0x04;
    }
}
