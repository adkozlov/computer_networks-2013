package coursework.common;

/**
 * @author adkozlov
 */
public final class Logger {
    private final static Logger INSTANCE = new Logger();

    public static Logger getInstance() {
        return INSTANCE;
    }

    private Logger() {
    }

    public void logException(Exception e) {
        System.err.println(e.getMessage());
    }
}
