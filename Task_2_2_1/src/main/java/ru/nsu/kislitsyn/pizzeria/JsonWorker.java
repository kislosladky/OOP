package ru.nsu.kislitsyn.pizzeria;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonWorker {
    private List<Pizzeria.Baker> bakers;
    private List<Pizzeria.Courier> couriers;

    public List<Pizzeria.Baker> getBakers() {
        return bakers;
    }

    public void setBakers(List<Pizzeria.Baker> bakers) {
        this.bakers = bakers;
    }

    public List<Pizzeria.Courier> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<Pizzeria.Courier> couriers) {
        this.couriers = couriers;
    }

    void readConfig(String bakersJson, String couriersJson) {
        try {
            String str = Files.readString(Paths.get(bakersJson));
            Type listOfBakers = new TypeToken<ArrayList<Pizzeria.Baker>>() {
            }.getType();
            this.bakers = new Gson().fromJson(str, listOfBakers);
            for (Pizzeria.Baker baker : this.bakers) {
                System.out.println(baker);
            }
        } catch (IOException notFound) {
            System.err.println(notFound.getLocalizedMessage());
        }

        try {
            String str = Files.readString(Paths.get(couriersJson));
            Type listOfBakers = new TypeToken<ArrayList<Pizzeria.Courier>>() {
            }.getType();
            this.couriers = new Gson().fromJson(str, listOfBakers);
            for (Pizzeria.Courier courier : couriers) {
                System.out.println(courier);
            }
        } catch (IOException notFound) {
            System.err.println(notFound.getLocalizedMessage());
        }


    }

    public static void main(String[] args) {
        JsonWorker jsonWorker = new JsonWorker();
        jsonWorker.readConfig("bakers.json", "couriers.json");
    }
}
