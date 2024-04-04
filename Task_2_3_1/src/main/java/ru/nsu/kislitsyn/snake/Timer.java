package ru.nsu.kislitsyn.snake;

import javafx.animation.AnimationTimer;

/**
 * Extends animation timer to add speed field for adjustment of animation velocity.
 */
public abstract class Timer extends AnimationTimer {
    private double speed;
    private long prevTime;

    /**
     * Setter for the speed.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Getter for the speed.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Getter for the timestamp of previous step of animation.
     */
    public long getPrevTime() {
        return prevTime;
    }

    /**
     * Setter for the timestamp of previous step of animation.
     */
    public void setPrevTime(long prevTime) {
        this.prevTime = prevTime;
    }

    /**
     * Simple constructor.
     */
    public Timer(long speed) {
        this.speed = speed;
    }
}
