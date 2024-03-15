package ru.nsu.kislitsyn.pizzeria;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * My implementation of queue, that is suitable for multi thread use.
 */
public class PizzeriaQueue<T> {
    public final Deque<T> orders;
    public final int capacity;

    /**
     * Simple constructor.
     */
    public PizzeriaQueue(int capacity) {
        this.orders = new ArrayDeque<>();
        this.capacity = capacity;
    }

    /**
     * You understand.
     */
    public boolean isEmpty() {
        return orders.isEmpty();
    }

    /**
     * Also obvious.
     */
    public boolean isFull() {
        return orders.size() == capacity;
    }

    /**
     * Adds entity to the queue.
     */
    public synchronized void addEntity(T newOrder) {
        while (this.isFull()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
        try {
            orders.addLast(newOrder);
        } finally {
            this.notifyAll();
        }


    }

    /**
     * Gets entity from queue and blocks, if entity is empty.
     */
    public synchronized T getEntity() {
        while (orders.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.out.println("The queue is interrupted");
                return null;
            }
        }
        try {
            return orders.pollFirst();
        } finally {
            this.notifyAll();
        }
    }

    /**
     * Gets entity from queue without blocking.
     */
    public synchronized T getEntityIfExists() {
        try {
            if (orders.isEmpty()) {
                return null;
            } else {
                return orders.pollFirst();
            }
        } finally {
            this.notifyAll();
        }
    }

}
