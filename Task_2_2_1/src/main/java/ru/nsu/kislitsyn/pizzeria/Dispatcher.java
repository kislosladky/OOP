package ru.nsu.kislitsyn.pizzeria;

import java.util.List;
import java.util.Random;

public class Dispatcher extends Thread implements Staff {
    private List<Order> orderList;
    private PizzeriaQueue<Order> orderQueue;

    public PizzeriaQueue<Order> getOrderQueue() {
        return orderQueue;
    }

    public void setOrderQueue(PizzeriaQueue<Order> orderQueue) {
        this.orderQueue = orderQueue;
    }
    public List<Order> getOrderList() {
        return orderList;
    }
    
    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

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
            } catch (InterruptedException e) {
                System.out.println("No more orders");
                return;
            }

            orderQueue.addEntity(order);
            System.out.println("Pizza number " + order.id + ", " + order.order + " is ordered");

        }
        System.out.println("No more orders");
    }
}
