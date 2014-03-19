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

    public String getCourseName() {
        return courseName;
    }

    public String getTaskName() {
        return taskName;
    }

    public FileWrapper getFileWrapper() {
        return fileWrapper;
    }
}
