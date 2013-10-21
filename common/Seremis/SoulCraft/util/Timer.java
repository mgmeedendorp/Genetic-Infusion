package Seremis.SoulCraft.util;


public class Timer {

    public boolean isTicking = false;
    public boolean timeStart = false;
    public int timeCount = 0;
    public boolean timerEnd = false;
    public int timerId;

    public Timer(int id, int maxTime) {
        startTimer(maxTime, id);
    }

    public void tick() {
        if(timeStart == true) {
            timeCount--;
            if(timeCount <= 0) {
                timeStart = false;
                timerEnd = true;
                isTicking = false;
            }
        }
    }

    public void startTimer(int ticks, int id) {
        if(!isTicking) {
            timeStart = true;
            timeCount = ticks;
            isTicking = true;
            timerId = id;
        }
    }
}
