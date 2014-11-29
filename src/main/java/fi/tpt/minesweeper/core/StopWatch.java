package fi.tpt.minesweeper.core;

/**
 * Created by timotapanainen on 15.11.14.
 */
public class StopWatch {

    private boolean started = false;
    private long startTime;
    private Long endTime;

    public void start() {
        startTime = System.currentTimeMillis();
        endTime = null;
        started = true;
    }

    public void stop() {
        if (!started)
            throw new IllegalStateException("start the watch first");
        endTime = System.currentTimeMillis();
        started = false;
    }

    /**
     * Returns stop watch time in milliseconds
     *
     * @return stop watch time
     */
    public long getTime() {
        if (started)
            return System.currentTimeMillis() - startTime;
        if (endTime != null)
            return endTime - startTime;
        else
            return 0;
    }
}
