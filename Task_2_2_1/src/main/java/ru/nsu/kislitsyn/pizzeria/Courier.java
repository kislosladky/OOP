package ru.nsu.kislitsyn.pizzeria;

import com.google.gson.annotations.Expose;

import java.util.ArrayDeque;
import java.util.Deque;

public class Courier implements Runnable, Staff {
    @Expose
    private int volume;
    private Deque<Order> pizzas = new ArrayDeque<>();
    private PizzeriaQueue<Order> pizzaStock;

    public PizzeriaQueue<Order> getPizzaStock() {
        return pizzaStock;
    }

    public void setPizzaStock(PizzeriaQueue<Order> pizzaStock) {
        this.pizzaStock = pizzaStock;
    }
    public Courier(Integer volume) {
        this.volume = volume;
    }
    @Override
    public void run() {
        pizzas = new ArrayDeque<>();
        while (!Thread.currentThread().isInterrupted()) {
            while (pizzas.size() < this.volume) {
                Order picked;
                synchronized (pizzaStock) {
                    while (pizzaStock.isEmpty()) {
                        try {
                            pizzaStock.wait();
                        } catch (InterruptedException e) {
                            System.err.println(e.getLocalizedMessage());
                            System.out.println("Courier couldn't wait and left");
                        }
                    }
                    picked = pizzaStock.getEntity();
                }
                System.out.println("Pizza number " + picked.id + ", "
                        + picked.order + ", is picked by courier");
                pizzas.push(picked);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException interruptedException) {
                interruptedException.getLocalizedMessage();
            }

            while (!pizzas.isEmpty()) {
                Order pizza = pizzas.pop();
                System.out.println("Pizza number " + pizza.id + ", "
                        + pizza.order + ", is delivered");
            }
        }


        //TODO написать, что делать курьеру по окончании времени работы
    }

    @Override
    public String toString() {
        return "Volume of bag: " + volume;
    }
}