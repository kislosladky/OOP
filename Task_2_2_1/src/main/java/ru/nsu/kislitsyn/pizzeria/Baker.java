package ru.nsu.kislitsyn.pizzeria;

import com.google.gson.annotations.Expose;

public class Baker extends Thread implements Staff {

    public int bakingSpeed;
//    @Expose(deserialize = false)
    private PizzeriaQueue<Order> orderQueue;
    @Expose(deserialize = false)
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

//    public Integer getBakingSpeed() {
//        return bakingSpeed;
//    }
//
//    public void setBakingSpeed(Integer bakingSpeed) {
//        this.bakingSpeed = bakingSpeed;
//    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            Order inWork = orderQueue.getEntity();
            System.out.println("Pizza number " + inWork.id() + ", "
                    + inWork.order() + ", is baking");
            try {
                Thread.sleep(bakingSpeed * 1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.getLocalizedMessage();
            }
            pizzaStock.addEntity(inWork);
            System.out.println("Pizza number " + inWork.id() + ", "
                    + inWork.order() + ", is moved to stock");
        }

        //TODO написать, что делать пекарю при окончании времени работы
    }

    @Override
    public String toString() {
        return "Speed: " + bakingSpeed;
    }
}
