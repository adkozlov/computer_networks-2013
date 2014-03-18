package coursework.common.model;

import coursework.common.FileWrapper;
import coursework.common.Signature;

/**
 * @author adkozlov
 */
public class SolutionRequest extends SignedObject {

    private final FileWrapper fileWrapper;

    public SolutionRequest(FileWrapper fileWrapper, Signature signature) {
        super(signature);
        this.fileWrapper = fileWrapper;
    }

    public FileWrapper getFileWrapper() {
        return fileWrapper;
    }
}
