package coursework.common.messages;

import coursework.common.model.Task;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class TaskMessage extends SignedObjectMessage {

    private final Task task;

    public TaskMessage(byte[] bytes) throws IOException {
        super(bytes);
        task = new Task(readString(dataInputStream), readString(dataInputStream), readString(dataInputStream), dataInputStream.readLong(), getSignature());
    }

    public TaskMessage(Task task) {
        super(task.getSignature());
        this.task = task;
    }

    @Override
    protected void writeMessage(DataOutputStream dataOutputStream) throws IOException {
        super.writeMessage(dataOutputStream);
        writeString(dataOutputStream, task.getCourseName());
        writeString(dataOutputStream, task.getTaskName());
        writeString(dataOutputStream, task.getText());
        dataOutputStream.writeLong(task.getDeadline());
    }

    public static final byte TYPE = 0x05;

    static {
        type = TYPE;
    }

    public Task getTask() {
        return task;
    }
}
