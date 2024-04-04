package ru.nsu.kislitsyn.pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BakerTest {
    @Test
    void simpleBakerTest() throws InterruptedException {
        PizzeriaQueue<Order> queue = new PizzeriaQueue<>(2);
        Order order = new Order();
        order.order = "Pizza";
        order.id = 2;
        queue.addEntity(order);
        PizzeriaQueue<Order> stock = new PizzeriaQueue<>(2);
        Baker baker = new Baker();
        baker.setPizzaStock(stock);
        baker.setOrderQueue(queue);
        baker.setBakingSpeed(3);
        baker.start();
        baker.interrupt();
        try {
            baker.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        assertTrue(queue.isEmpty());
        assertEquals(order, stock.getEntity());
    }
}