package seremis.geninfusion.util;

public class Timer {

    public int timerId;

    private long currTime = 0;
    private long lastUpdateTick = 0;
    private long ticksBeforeUpdate;

    public int iterationsLeft;

    private ITimerCaller caller;

    public Timer(int timerId, int ticksBeforeUpdate, int iterations, ITimerCaller caller) {
        this.timerId = timerId;
        this.ticksBeforeUpdate = ticksBeforeUpdate;
        this.iterationsLeft = iterations;
        this.caller = caller;
    }

    public void tick() {
        currTime++;
        if(lastUpdateTick + ticksBeforeUpdate <= currTime) {
            if(iterationsLeft > 0) {
                lastUpdateTick = currTime++;
                iterationsLeft--;
                caller.timerTick(this);
            }
        }
    }
}
