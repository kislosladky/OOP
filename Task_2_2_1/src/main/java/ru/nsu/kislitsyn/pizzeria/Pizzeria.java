package ru.nsu.kislitsyn.pizzeria;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * The main class that runs the threads and interrupts it in the end.
 */
public class Pizzeria {
    public List<Baker> bakers;
    public List<Courier> couriers;
    public final int stockCapacity;
    public final PizzeriaQueue<Order> orders;
    public final PizzeriaQueue<Order> pizzaStock;
    public Dispatcher dispatcher;
    public final long workTime;

    /**
     * Constructor of pizzeria.
     *
     * @param stockCapacity capacity of stock
     * @param workTime time of work of pizzeria
     */
    public Pizzeria(int stockCapacity, long workTime) {
        this.stockCapacity = stockCapacity;
        orders = new PizzeriaQueue<>(Integer.MAX_VALUE);
        pizzaStock = new PizzeriaQueue<>(stockCapacity);
        this.workTime = workTime;
    }

    /**
     * Reads staff config from jsons.
     */
    public void getConfiguration(String bakersFilename,
                                 String couriersFilename,
                                 String dispatcherFilename) {
        try {
            this.bakers = JsonWorker.readBakers(Files
                    .readString(Paths.get(bakersFilename)));
            this.couriers = JsonWorker.readCouriers(Files
                    .readString(Paths.get(couriersFilename)));
            this.dispatcher = JsonWorker.readDispatcher(Files
                    .readString(Paths.get(dispatcherFilename)));
        } catch (IOException e) {
            System.err.println("Couldn't read a file");
            return;
        }

        for (Baker baker : bakers) {
            baker.setOrderQueue(orders);
            baker.setPizzaStock(pizzaStock);
        }

        for (Courier courier : couriers) {
            courier.setPizzaStock(pizzaStock);
        }

        assert dispatcher != null; //just because of warning
        dispatcher.setOrderQueue(orders);
    }

    /**
     * Starts staff threads and interrupts it in the end.
     */
    public void work() {
        dispatcher.start();

        for (Baker baker : bakers) {
            baker.start();
        }
        List<Thread> courierThreads = new ArrayList<>();

        for (Courier courier : couriers) {
            Thread thread = new Thread(courier);
            thread.start();
            courierThreads.add(thread);
        }

        try {
            Thread.sleep(workTime * 1000);
        } catch (InterruptedException interruptedException) {
            interruptedException.getLocalizedMessage();
        }

        dispatcher.interrupt();
        for (Baker baker : bakers) {
            baker.interrupt();
        }

        for (Thread thread : courierThreads) {
            thread.interrupt();
        }

        System.out.println("Pizzeria is closed");
    }

    /**
     * Main.
     */
    public static void main(String[] args) {
        Pizzeria pizzeria = new Pizzeria(10, 14);
        pizzeria.getConfiguration("bakers.json",
                "couriers.json", "dispatcher.json");
        pizzeria.work();
    }

}