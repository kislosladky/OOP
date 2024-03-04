package ru.nsu.kislitsyn.pizzeria;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Pizzeria {
    private List<Baker> bakers;
    private List<Courier> couriers;
    private final int stockCapacity;
    private final PizzeriaQueue<Order> orders;
    private final PizzeriaQueue<Order> pizzaStock;

    public Pizzeria(int stockCapacity) {
        this.stockCapacity = stockCapacity;
        orders = new PizzeriaQueue<>(Integer.MAX_VALUE);
        pizzaStock = new PizzeriaQueue<>(stockCapacity);
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

    public void getConfiguration(String bakersFilename, String couriersFilename) {
        JsonWorker jsonWorker = new JsonWorker();
        jsonWorker.readConfig(bakersFilename, couriersFilename);
        this.bakers = jsonWorker.getBakers();
        this.couriers = jsonWorker.getCouriers();
    }

    public class Baker implements Runnable{
        private String name;
        private Integer bakingSpeed;
//        private boolean isFree;
        private Order inWork;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getBakingSpeed() {
            return bakingSpeed;
        }

        public void setBakingSpeed(Integer bakingSpeed) {
            this.bakingSpeed = bakingSpeed;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                inWork = orders.getEntity();
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
        }

        @Override
        public String toString() {
            return "Name: " + name + "\nSpeed: "+ bakingSpeed.toString();
        }
    }

    public class Courier implements Runnable {
        private Integer volume;
//        private boolean isFree;
        private Deque<Order> pizzas = new ArrayDeque<>();
        public Courier(Integer volume) {
            this.volume = volume;
//            this.isFree = true;
        }
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                while (pizzas.size() < this.volume) {
                    Order picked = pizzaStock.getEntity();
                    System.out.println("Pizza number " + picked.id() + ", "
                            + picked.order() + ", is picked by courier");
                    pizzas.push(picked);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.getLocalizedMessage();
                }

                while (!pizzas.isEmpty()) {
                    Order pizza = pizzas.pop();
                    System.out.println("Pizza number " + pizza.id() + ", "
                            + pizza.order() + ", is delivered");
                }
            }
        }

        @Override
        public String toString() {
            return "Volume: " + volume.toString();
        }
    }
    public static void main(String[] args) {
        String bakersFilename = "bakers.json";
        String couriersFilename = "couriers.json";


    }

}