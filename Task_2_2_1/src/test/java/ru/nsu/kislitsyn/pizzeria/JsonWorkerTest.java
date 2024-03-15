package ru.nsu.kislitsyn.pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;


class JsonWorkerTest {
    @Test
    void jsonBakersTest() {
        String input = """
                [
                  {
                    "bakingSpeed": 2
                  },
                  {
                    "bakingSpeed": 4
                  }
                ]""";
        List<Baker> bakerList = JsonWorker.readBakers(input);
        assertEquals(2, bakerList.get(0).getBakingSpeed());
        assertEquals(4, bakerList.get(1).getBakingSpeed());
        assertEquals(2, bakerList.size());
    }

    @Test
    void jsonCouriersTest() {
        String input = """
                [
                  {
                    "volume": 2
                  },
                  {
                    "volume": 3
                  }
                ]""";
        List<Courier> courierList = JsonWorker.readCouriers(input);
        assertEquals(2, courierList.get(0).getVolume());
        assertEquals(3, courierList.get(1).getVolume());
        assertEquals(2, courierList.size());
    }

    @Test
    void jsonDispatcherTest() {
        String input = """
                [
                  {
                    "id": 1,
                    "order": "pepperoni"
                  },
                  {
                    "id": 2,
                    "order": "margarita"
                  },
                  {
                    "id": 3,
                    "order": "4 cheeses"
                  }
                ]""";
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