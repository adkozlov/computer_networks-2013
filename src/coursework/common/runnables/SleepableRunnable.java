package coursework.common.runnables;

import coursework.common.Logger;

/**
 * @author adkozlov
 */
public abstract class SleepableRunnable implements Runnable {

    private static final long DEFAULT_SLEEP_PERIOD = 250;

    protected void sleep() {
        sleep(DEFAULT_SLEEP_PERIOD);
    }

    protected void sleep(long sleepPeriod) {
        try {
            Thread.sleep(sleepPeriod);
        } catch (InterruptedException e) {
            Logger.getInstance().logException(e);
        }
    }
}
