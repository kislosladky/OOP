package ru.nsu.kislitsyn.pizzeria;

import java.util.List;
import java.util.Random;

/**
 * Thread of dispatcher, that adds orders from json to the queue.
 */
public class Dispatcher extends Thread implements Staff {
    private List<Order> orderList;
    private PizzeriaQueue<Order> orderQueue;

    /**
     * Setter of queue.
     */
    public void setOrderQueue(PizzeriaQueue<Order> orderQueue) {
        this.orderQueue = orderQueue;
    }

    /**
     * getter of list of orders.
     */
    public List<Order> getOrderList() {
        return orderList;
    }

    /**
     * Setter for list of orders.
     */
    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    /**
     * The main method of dispatcher that adds orders to the queue.
     */
    @Override
    public void run() {
        Random random = new Random();
        for (Order order : orderList) {
            if (this.isInterrupted()) {
                System.out.println("No more orders");
                return;
            }

            try {
                Thread.sleep((random.nextInt(3) + 1) * 1000);
                orderQueue.addEntity(order);
            } catch (InterruptedException e) {
                System.out.println("No more orders");
                return;
            }
            System.out.println("Pizza number " + order.id + ", " + order.order + " is ordered");

        }
        System.out.println("No more orders");
    }
}
