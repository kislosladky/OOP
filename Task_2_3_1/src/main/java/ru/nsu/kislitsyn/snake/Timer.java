package ru.nsu.kislitsyn.snake;

import javafx.animation.AnimationTimer;

public abstract class Timer extends AnimationTimer {
    private long speed;
    private long prevTime;

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public long getSpeed() {
        return speed;
    }

    public long getPrevTime() {
        return prevTime;
    }

    public void setPrevTime(long prevTime) {
        this.prevTime = prevTime;
    }

    public Timer(long speed) {
        this.speed = speed;
    }
}
