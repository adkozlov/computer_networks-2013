package coursework.common;

/**
 * @author adkozlov
 */
public class FileWrapper {

    private final String fileName;
    private final byte[] content;

    public FileWrapper(String fileName, byte[] content) {
        this.fileName = fileName;
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getContent() {
        return content;
    }
}
