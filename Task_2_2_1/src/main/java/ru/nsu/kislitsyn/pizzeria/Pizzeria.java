package ru.nsu.kislitsyn.pizzeria;

import java.util.List;
public class Pizzeria {
    public List<Baker> bakers;
    public List<Courier> couriers;
    public final int stockCapacity;
    public final PizzeriaQueue<Order> orders;
    public final PizzeriaQueue<Order> pizzaStock;
    public Dispatcher dispatcher;

    public final long workTime;

    public Pizzeria(int stockCapacity, long workTime) {
        this.stockCapacity = stockCapacity;
        orders = new PizzeriaQueue<>(Integer.MAX_VALUE);
        pizzaStock = new PizzeriaQueue<>(stockCapacity);
        this.workTime = workTime;
    }

    public int getStockCapacity() {
        return stockCapacity;
    }

    public void addOrder(Order orderToAdd) {
        orders.addEntity(orderToAdd);
    }

    public Order getOrder() {
        return orders.getEntity();
    }

    public void addPizza(Order pizza) {
        pizzaStock.addEntity(pizza);
    }

    public Order getPizza() {
        return pizzaStock.getEntity();
    }

    public List<Baker> getBakers() {
        return bakers;
    }

    public void setBakers(List<Baker> bakers) {
        this.bakers = bakers;
    }

    public List<Courier> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<Courier> couriers) {
        this.couriers = couriers;
    }

    public void getConfiguration(String bakersFilename, String couriersFilename, String dispatcherFilename) {
        this.bakers = JsonWorker.readBakers(bakersFilename);
        this.couriers = JsonWorker.readCouriers(couriersFilename);
        this.dispatcher = JsonWorker.readDispatcher(dispatcherFilename);

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

    public void work() {
        dispatcher.start();

        for (Baker baker : bakers) {
            baker.start();
        }

        for (Courier courier : couriers) {
            courier.start();
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

        for (Courier courier : couriers) {
            courier.interrupt();
        }
    }

    public static void main(String[] args) {
        Pizzeria pizzeria = new Pizzeria(10, 12);
        pizzeria.getConfiguration("bakers.json", "couriers.json", "dispatcher.json");
        pizzeria.work();
    }

}