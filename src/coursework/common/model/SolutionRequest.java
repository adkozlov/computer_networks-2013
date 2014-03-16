package coursework.common.model;

import coursework.common.FileWrapper;

/**
 * @author adkozlov
 */
public class SolutionRequest {

    private final FileWrapper fileWrapper;

    public SolutionRequest(FileWrapper fileWrapper) {
        this.fileWrapper = fileWrapper;
    }

    public FileWrapper getFileWrapper() {
        return fileWrapper;
    }
}
