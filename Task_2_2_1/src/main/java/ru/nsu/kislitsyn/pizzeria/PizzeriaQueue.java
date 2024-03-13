package ru.nsu.kislitsyn.pizzeria;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PizzeriaQueue<T> {
    public final Deque<T> orders;
    public final int capacity;

    public PizzeriaQueue(int capacity) {
        this.orders = new ArrayDeque<>();
        this.capacity = capacity;
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }

    public boolean isFull() {
        return orders.size() == capacity;
    }
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

    public synchronized T getEntity() {
        while (orders.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
        try {
            return orders.pollFirst();
        } finally {
            this.notifyAll();
        }
    }

}
