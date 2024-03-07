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
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            while (orders.size() >= this.capacity) {
                notFull.await();
            }
            orders.addLast(newOrder);
        } catch (InterruptedException e) {
            e.getLocalizedMessage();
        } finally {
            notEmpty.signal();
            lock.unlock();
        }
    }

    public T getEntity() {
        final ReentrantLock lock = this.lock;
        lock.lock();

        try {
            while (orders.isEmpty()) {
                notEmpty.await();
            }
        } catch (InterruptedException e) {
            e.getLocalizedMessage();
        }
        try {
            return orders.pollFirst();
        } finally {
            notFull.signal();
            lock.unlock();
        }
    }


}
