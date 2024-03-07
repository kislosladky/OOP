package ru.nsu.kislitsyn.pizzeria;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonWorker {
    public static List<Baker> readBakers(String bakersJson) {
        List<Baker> bakers;
        try {
            String str = Files.readString(Paths.get(bakersJson));
            Type listOfBakers = new TypeToken<ArrayList<Baker>>() {
            }.getType();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//            Gson gson = new Gson();
            bakers = gson.fromJson(str, listOfBakers);
            for (Baker baker : bakers) {
                System.out.println(baker);
            }
        } catch (IOException notFound) {
            System.err.println(notFound.getLocalizedMessage());
            return null;
        }
        return bakers;
    }

    public static List<Courier> readCouriers(String couriersJson) {
        List<Courier> couriers = null;
        try {
            String str = Files.readString(Paths.get(couriersJson));
            Type listOfBakers = new TypeToken<ArrayList<Courier>>() {
            }.getType();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            //Gson gson = new Gson();
            couriers = gson.fromJson(str, listOfBakers);
            for (Courier courier : couriers) {
                System.out.println(courier);
            }
        } catch (IOException notFound) {
            System.err.println(notFound.getLocalizedMessage());
        }
        return couriers;
    }

    public static Dispatcher readDispatcher(String dispatcherJson) {
        Dispatcher dispatcher = new Dispatcher();
        try {
            String str = Files.readString(Paths.get(dispatcherJson));
            Type orderList = new TypeToken<ArrayList<Order>>() {}.getType();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            dispatcher.setOrderList(gson.fromJson(str, orderList));
            List<Order> orders = dispatcher.getOrderList();
            System.out.println("Orders");
            for (Order order : orders) {
                System.out.println("Id: " + order.id + ", order: " + order.order);
            }
        } catch (IOException ioException) {
            ioException.getLocalizedMessage();
            return null;
        }
        return dispatcher;
    }
}
