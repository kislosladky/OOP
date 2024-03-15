package ru.nsu.kislitsyn.pizzeria;

import com.google.gson.annotations.Expose;

/**
 * Thread that bakes pizza from order queue and puts it into stock.
 */
public class Baker extends Thread implements Staff {

    @Expose
    private int bakingSpeed;
    private PizzeriaQueue<Order> orderQueue;
    private PizzeriaQueue<Order> pizzaStock;

    /**
     * Setter of stock.
     *
     * @param pizzaStock stock of pizzas.
     */
    public void setPizzaStock(PizzeriaQueue<Order> pizzaStock) {
        this.pizzaStock = pizzaStock;
    }

    /**
     * Setter for queue of pizzas.
     *
     * @param orderQueue queue to set.
     */
    public void setOrderQueue(PizzeriaQueue<Order> orderQueue) {
        this.orderQueue = orderQueue;
    }

    /**
     * Setter for baking speed that is used by Gson.
     *
     * @param bakingSpeed int to set.
     */
    public void setBakingSpeed(Integer bakingSpeed) {
        this.bakingSpeed = bakingSpeed;
    }

    /**
     * Simple getter.
     */
    public int getBakingSpeed() {
        return bakingSpeed;
    }
    /**
     * The main method of the class.
     */
    @Override
    public void run() {
        while (true) {
            Order inWork = getOrderFromQueue();

            if (inWork == null) {
                System.out.println("Baker finished work");
                return;
            }

            System.out.println("Pizza number " + inWork.id + ", "
                    + inWork.order + ", is baking");
            try {
                Thread.sleep(bakingSpeed * 1000);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }

            addPizzaToStock(inWork);
        }
    }

    /**
     * Gets order form the queue.
     */
    private Order getOrderFromQueue() {
        Order inWork;
        synchronized (orderQueue) {
            if (Thread.currentThread().isInterrupted()) {
                inWork = orderQueue.getEntityIfExists();
            } else {
                while (orderQueue.isEmpty()) {
                    try {
                        orderQueue.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Baker is interrupted");
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                inWork = orderQueue.getEntity();
            }
        }
        return inWork;
    }

    /**
     * Adds the order to the queue.
     */
    private void addPizzaToStock(Order inWork) {
        synchronized (pizzaStock) {
            while (pizzaStock.isFull()) {
                try {
                    pizzaStock.wait();
                } catch (InterruptedException e) {
                    System.out.println("Baker is interrupted");
                    Thread.currentThread().interrupt();
                }
            }
            pizzaStock.addEntity(inWork);
        }
        System.out.println("Pizza number " + inWork.id + ", "
                + inWork.order + ", is moved to stock");
    }

    /**
     * Used to check the baker after deserealisation.
     *
     * @return baking speed of the baker.
     */
    @Override
    public String toString() {
        return "Speed: " + bakingSpeed;
    }
}
