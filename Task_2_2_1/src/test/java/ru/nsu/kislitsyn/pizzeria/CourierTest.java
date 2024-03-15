package ru.nsu.kislitsyn.pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;


class CourierTest {
    OutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(output));
    }

    @AfterAll
    public static void tearDown() {
        System.setOut(System.out);
    }
    @Test
    void simpleCourierTest() {
        Order order = new Order();
        order.order = "Pizza";
        order.id = 2;
        PizzeriaQueue<Order> stock = new PizzeriaQueue<>(2);
        stock.addEntity(order);
        Courier courier = new Courier(3);
        courier.setPizzaStock(stock);
        Thread thread = new Thread(courier);
        thread.start();
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        assertTrue(stock.isEmpty());
        assertEquals("Pizza number 2, Pizza, is picked by courier\nCourier finished the work", output.toString().trim());
    }

}