package ru.nsu.kislitsyn.pizzeria;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PizzeriaQueue<T> {
    public final Deque<T> orders;
    public final int capacity;
    public ReentrantLock lock = new ReentrantLock();
    public final Condition notEmpty = lock.newCondition();
    public final Condition notFull = lock.newCondition();
    public PizzeriaQueue(int capacity) {
        this.orders = new ArrayDeque<>();
        this.capacity = capacity;
    }

    public void addEntity(T newOrder) {
        if (orders.size() >= this.capacity) {
            try {
                notFull.await();
            } catch (InterruptedException e) {
                e.getLocalizedMessage();
            }
        }
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            orders.addLast(newOrder);
        } finally {
            lock.unlock();
        }
        notEmpty.signal();
    }

    public T getEntity() {
        final ReentrantLock lock = this.lock;
        if (orders.isEmpty()) {
            try {
                notEmpty.await();
            } catch (InterruptedException e) {
                e.getLocalizedMessage();
            }
        }
        lock.lock();
        try {
            return orders.pollFirst();
        } finally {
            lock.unlock();
            notFull.signal();
        }
    }


}
