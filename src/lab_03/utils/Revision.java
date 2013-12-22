package lab_03.utils;

import java.io.File;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * @author adkozlov
 */
public final class Revision {

    private final BigInteger version;
    private final Set<File> files;

    public Revision() {
        version = BigInteger.ZERO;
        files = new HashSet<>();
    }

    public byte[] getRevisionListHash() {
        return null;
    }

    public void put(File file) {
        files.add(file);
        version.add(BigInteger.ONE);
    }

    public void delete(File file) {

    }

}
