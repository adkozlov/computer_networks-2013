package coursework.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author adkozlov
 */
public final class Utils {

    public static SimpleDateFormat dateFormat() {
        return new SimpleDateFormat(Configuration.DATE_FORMAT);
    }

    public static String longToDateString(long date) {
        return dateFormat().format(new Date(date));
    }

    public static String nowToDateString() {
        return longToDateString(System.currentTimeMillis());
    }

    public static String fromBytes(byte[] bytes) {
        return new String(bytes, Configuration.UTF8_CHARSET);
    }

    public static byte[] getBytes(String s) {
        return s.getBytes(Configuration.UTF8_CHARSET);
    }

    private static void cleanDirectory(Path path) throws IOException {
        File file = path.toFile();

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                cleanDirectory(f.toPath());
            }
        }

        Files.deleteIfExists(path);
    }

    public static void cleanDirectory(String pathString) {
        try {
            cleanDirectory(Paths.get(pathString));
        } catch (IOException e) {
            Logger.getInstance().logException(e);
        }
    }
}
