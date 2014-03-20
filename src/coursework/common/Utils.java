package coursework.common;

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
}
