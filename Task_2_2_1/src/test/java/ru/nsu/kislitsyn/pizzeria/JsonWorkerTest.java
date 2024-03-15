package ru.nsu.kislitsyn.pizzeria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class JsonWorkerTest {
    @Test
    void jsonBakersTest() {
        String input = "[\n"
                + "  {\n"
                + "    \"bakingSpeed\": 2\n"
                + "  },\n"
                + "  {\n"
                + "    \"bakingSpeed\": 4\n"
                + "  }\n]";
        List<Baker> bakerList = JsonWorker.readBakers(input);
        assertEquals(2, bakerList.get(0).getBakingSpeed());
        assertEquals(4, bakerList.get(1).getBakingSpeed());
        assertEquals(2, bakerList.size());
    }

    @Test
    void jsonCouriersTest() {
        String input = "[\n"
                + "  {\n"
                + "    \"volume\": 2\n"
                + "  },\n"
                + "  {\n"
                + "    \"volume\": 3\n"
                + "  }\n]";
        List<Courier> courierList = JsonWorker.readCouriers(input);
        assertEquals(2, courierList.get(0).getVolume());
        assertEquals(3, courierList.get(1).getVolume());
        assertEquals(2, courierList.size());
    }

    @Test
    void jsonDispatcherTest() {
        String input = "[\n"
                + "  {\n"
                + "    \"id\": 1,\n"
                + "    \"order\": \"pepperoni\"\n"
                + "  },\n"
                + "  {\n"
                + "    \"id\": 2,\n"
                + "    \"order\": \"margarita\"\n"
                + "  },\n"
                + "  {\n"
                + "    \"id\": 3,\n"
                + "    \"order\": \"4 cheeses\"\n"
                + "  }\n]";
        Dispatcher dispatcher = JsonWorker.readDispatcher(input);
        assertEquals(3, dispatcher.getOrderList().size());
        assertEquals(1, dispatcher.getOrderList().get(0).id);
        assertEquals(2, dispatcher.getOrderList().get(1).id);
        assertEquals(3, dispatcher.getOrderList().get(2).id);
        assertEquals("pepperoni", dispatcher.getOrderList().get(0).order);
        assertEquals("margarita", dispatcher.getOrderList().get(1).order);
        assertEquals("4 cheeses", dispatcher.getOrderList().get(2).order);
    }
}