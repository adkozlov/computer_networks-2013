package coursework.common;

/**
 * @author adkozlov
 */
public final class Utils {

    public static void startRunnable(Runnable runnable) {
        new Thread(runnable).start();
    }
}
