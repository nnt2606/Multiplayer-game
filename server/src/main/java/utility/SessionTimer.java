package utility;

import properties.Config;
import session.Session;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Timer is a class used to count time in minute.
 */
public class SessionTimer implements Runnable {
    private AtomicInteger count = new AtomicInteger(0);
    private boolean isRunning;
    private Session session;

    public SessionTimer(Session session) {
        this.session = session;
    }

    /**
     * Start the timer so that the timer start tickling for each minute pass.
     */
    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            try {
                TimeUnit.SECONDS.sleep(1);
                int currentValue = count.addAndGet(1);
                if (currentValue == Config.SESSION_TIME_OUT) {
                    this.session.onDisconnect(false);
                    isRunning = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    /**
     * Stop the timer.
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * Reset the count currently in the timer
     */
    public void reset() {
        count.set(0);
    }
}
