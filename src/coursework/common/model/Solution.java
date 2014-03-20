package coursework.common.model;

import coursework.common.FileWrapper;
import coursework.common.Signature;

/**
 * @author adkozlov
 */
public class Solution extends SignedObject {

    private final String courseName;
    private final String taskName;
    private final FileWrapper fileWrapper;

    public Solution(String courseName, String taskName, FileWrapper fileWrapper, Signature signature) {
        super(signature);
        this.courseName = courseName;
        this.taskName = taskName;
        this.fileWrapper = fileWrapper;
    }

    public Solution(Solution solution, Signature signature) {
        super(signature);
        courseName = solution.getCourseName();
        taskName = solution.getTaskName();
        fileWrapper = solution.getFileWrapper();
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTaskName() {
        return taskName;
    }

    public FileWrapper getFileWrapper() {
        return fileWrapper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Solution)) return false;

        Solution solution = (Solution) o;

        if (courseName != null ? !courseName.equals(solution.courseName) : solution.courseName != null) return false;
        if (fileWrapper != null ? !fileWrapper.equals(solution.fileWrapper) : solution.fileWrapper != null)
            return false;
        if (taskName != null ? !taskName.equals(solution.taskName) : solution.taskName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = courseName != null ? courseName.hashCode() : 0;
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (fileWrapper != null ? fileWrapper.hashCode() : 0);
        return result;
    }
}
