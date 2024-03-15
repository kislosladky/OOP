package ru.nsu.kislitsyn.pizzeria;

import com.google.gson.annotations.Expose;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Thread of courier.
 */
public class Courier implements Runnable, Staff {
    @Expose
    private int volume;
    private Deque<Order> pizzas = new ArrayDeque<>();
    private PizzeriaQueue<Order> pizzaStock;


    /**
     * Setter for stock.
     */
    public void setPizzaStock(PizzeriaQueue<Order> pizzaStock) {
        this.pizzaStock = pizzaStock;
    }

    /**
     * Simple constructor.
     */
    public Courier(Integer volume) {
        this.volume = volume;
    }

    /**
     * Main function od courier's fork.
     */
    @Override
    public void run() {
        pizzas = new ArrayDeque<>();
        while(true) {
            while (pizzas.size() < this.volume) {
                Order picked;
                synchronized (pizzaStock) {

                    if (Thread.currentThread().isInterrupted()) {
                        picked = pizzaStock.getEntityIfExists();
                    } else {
                        while (pizzaStock.isEmpty()) {
                            try {
                                pizzaStock.wait();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                System.out.println("Courier is interrupted");
                                break;
                            }
                        }
                        picked = pizzaStock.getEntity();
                    }
                }
                if (picked == null) {
                    System.out.println("Courier finished the work");
                    return;
                }

                System.out.println("Pizza number " + picked.id + ", "
                        + picked.order + ", is picked by courier");
                pizzas.push(picked);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException interruptedException) {
                System.out.println("Courier is interrupted");
                Thread.currentThread().interrupt();
            }

            while (!pizzas.isEmpty()) {
                Order pizza = pizzas.pop();
                System.out.println("Pizza number " + pizza.id + ", "
                        + pizza.order + ", is delivered");
            }
        }
    }

    /**
     * Simple toString override.
     */
    @Override
    public String toString() {
        return "Volume of bag: " + volume;
    }
}