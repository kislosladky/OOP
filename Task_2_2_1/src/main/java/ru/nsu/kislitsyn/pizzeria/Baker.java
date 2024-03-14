package ru.nsu.kislitsyn.pizzeria;

import com.google.gson.annotations.Expose;

public class Baker extends Thread implements Staff {

    @Expose
    private int bakingSpeed;
//    @Expose(deserialize = false)
    private PizzeriaQueue<Order> orderQueue;
    private PizzeriaQueue<Order> pizzaStock;

    public PizzeriaQueue<Order> getPizzaStock() {
        return pizzaStock;
    }

    public void setPizzaStock(PizzeriaQueue<Order> pizzaStock) {
        this.pizzaStock = pizzaStock;
    }

    public PizzeriaQueue<Order> getOrderQueue() {
        return orderQueue;
    }

    public void setOrderQueue(PizzeriaQueue<Order> orderQueue) {
        this.orderQueue = orderQueue;
    }

    public Integer getBakingSpeed() {
        return bakingSpeed;
    }

    public void setBakingSpeed(Integer bakingSpeed) {
        this.bakingSpeed = bakingSpeed;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            Order inWork;
            synchronized (orderQueue) {
                while (orderQueue.isEmpty()) {
                    try {
                        orderQueue.wait();
                    } catch (InterruptedException e) {
                        System.err.println("Baker finished work");
                        return;
                    }
                }
                if (Thread.currentThread().isInterrupted()) {
                    inWork = orderQueue.getEntityIfExists();
                } else {
                    inWork = orderQueue.getEntity();
                }
            }
            if (inWork == null) {
                System.out.println("Baker finished work");
                return;
            }
            System.out.println("Pizza number " + inWork.id + ", "
                    + inWork.order + ", is baking");
            try {
                Thread.sleep(bakingSpeed * 1000);
            } catch (InterruptedException interruptedException) {
                System.err.println("Baker finished work");
                return;
            }

            synchronized (pizzaStock) {
                while (pizzaStock.isFull()) {
                    try {
                        pizzaStock.wait();
                    } catch (InterruptedException e) {
                        System.err.println("Baker is interrupted");
                    }
                }
                pizzaStock.addEntity(inWork);
            }
            System.out.println("Pizza number " + inWork.id + ", "
                    + inWork.order + ", is moved to stock");
        }

        //TODO написать, что делать пекарю при окончании времени работы
    }

    @Override
    public String toString() {
        return "Speed: " + bakingSpeed;
    }
}
