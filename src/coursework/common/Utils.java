package coursework.common;

/**
 * @author adkozlov
 */
public final class Utils {

    public static void startRunnable(Runnable runnable) {
        new Thread(runnable).start();
    }

    public static void startRunnables(Runnable[] runnables) {
        for (Runnable runnable : runnables) {
            startRunnable(runnable);
        }
    }
}
