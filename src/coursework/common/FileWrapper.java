package coursework.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileWrapper)) return false;

        FileWrapper that = (FileWrapper) o;

        if (!Arrays.equals(content, that.content)) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (content != null ? Arrays.hashCode(content) : 0);
        return result;
    }
}
