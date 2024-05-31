package ru.nsu.kislitsyn.pizzeria;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Deserelizes configs from jsons.
 */
public class JsonWorker {
    /**
     * Reads bakers from json.
     */
    public static List<Baker> readBakers(String bakersJson) {
        List<Baker> bakers;
        Type listOfBakers = new TypeToken<ArrayList<Baker>>() {
        }.getType();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        bakers = gson.fromJson(bakersJson, listOfBakers);
        for (Baker baker : bakers) {
            System.out.println(baker);
        }

        return bakers;
    }

    /**
     * Reads couriers from json.
     */
    public static List<Courier> readCouriers(String couriersJson) {
        List<Courier> couriers = null;
        Type listOfBakers = new TypeToken<ArrayList<Courier>>() {
        }.getType();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        couriers = gson.fromJson(couriersJson, listOfBakers);
        for (Courier courier : couriers) {
            System.out.println(courier);
        }
        return couriers;
    }

    /**
     * Reads dispatcher from json.
     */
    public static Dispatcher readDispatcher(String dispatcherJson) {
        Dispatcher dispatcher = new Dispatcher();
        Type orderList = new TypeToken<ArrayList<Order>>() {
        }.getType();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        dispatcher.setOrderList(gson.fromJson(dispatcherJson, orderList));
        List<Order> orders = dispatcher.getOrderList();
        System.out.println("Orders");
        for (Order order : orders) {
            System.out.println("Id: " + order.id + ", order: " + order.order);
        }
        return dispatcher;
    }
}
