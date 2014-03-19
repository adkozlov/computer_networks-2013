package coursework.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author adkozlov
 */
public class FileWrapper {

    private final String fileName;
    private final byte[] content;

    public FileWrapper(File file) throws IOException {
        fileName = file.getName();
        content = Files.readAllBytes(file.toPath());
    }

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
