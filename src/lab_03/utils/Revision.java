package lab_03.utils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author adkozlov
 */
public final class Revision {

    private int version;
    private final Set<File> files;

    public Revision() {
        version = 0;
        files = new HashSet<>();
    }

    public int getVersion() {
        return version;
    }

    public Set<File> getFiles() {
        return files;
    }

    public byte[] getRevisionListHash() {
        return null;
    }

    public void put(File file) {
        files.add(file);
        version += 1;
    }

    public void delete(File file) {

    }

}
