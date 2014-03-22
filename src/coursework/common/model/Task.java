package coursework.common.model;

import coursework.common.Signature;

/**
 * @author adkozlov
 */
public class Task extends SignedObject {

    private final String courseName;
    private final String taskName;
    private final String text;
    private final long deadline;

    public Task(String courseName, String taskName, String text, long deadline, Signature signature) {
        super(signature);
        this.courseName = courseName;
        this.taskName = taskName;
        this.text = text;
        this.deadline = deadline;
    }

//    public Task(Task task, Signature signature) {
//        super(signature);
//        courseName = task.getCourseName();
//        taskName = task.getTaskName();
//        text = task.getText();
//        deadline = task.getDeadline();
//    }

    public String getCourseName() {
        return courseName;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getText() {
        return text;
    }

    public long getDeadline() {
        return deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        if (!super.equals(o)) return false;

        Task task = (Task) o;

        if (deadline != task.deadline) return false;
        if (courseName != null ? !courseName.equals(task.courseName) : task.courseName != null) return false;
        if (taskName != null ? !taskName.equals(task.taskName) : task.taskName != null) return false;
        if (text != null ? !text.equals(task.text) : task.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (int) (deadline ^ (deadline >>> 32));
        return result;
    }
}
