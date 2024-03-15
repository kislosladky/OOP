package ru.nsu.kislitsyn.pizzeria;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;


class DispatcherTest {
    @Test
    void simpleDispatcherTest() {
        Dispatcher dispatcher = new Dispatcher();
        PizzeriaQueue<Order> queue = new PizzeriaQueue<>(2);
        dispatcher.setOrderQueue(queue);
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        order.id = 4;
        order.order = "Margarita";
        orderList.add(order);
        dispatcher.setOrderList(orderList);
        dispatcher.start();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        dispatcher.interrupt();
        try {
            dispatcher.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        assertEquals(order, orderList.get(0));
        assertEquals(order, queue.getEntityIfExists());
    }
}