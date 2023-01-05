package utility;

import entity.game.Game;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Timer is a class used to count time in minute.
 */
public class GameTimer implements Runnable {
    private AtomicInteger count = new AtomicInteger(0);
    private boolean isRunning;
    private Game game;

    public GameTimer(Game game) {
        this.game = game;
    }

    /**
     * Start the timer so that the timer start tickling for each minute pass.
     */
    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            try {
                TimeUnit.MILLISECONDS.sleep(this.game.getGameLength() * 1000);
//                int currentValue = count.addAndGet(1);
//                if (currentValue == this.game.getGameLength()) {
                    this.game.endGame();
                    isRunning = false;
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
