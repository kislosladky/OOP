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
    public synchronized void addEntity(T newOrder) throws InterruptedException {
        while (this.isFull()) {
            this.wait();
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
    public synchronized T getEntity() throws InterruptedException {
        while (orders.isEmpty()) {
                this.wait();
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
            return orders.pollFirst();
        } finally {
            this.notifyAll();
        }
    }

}
