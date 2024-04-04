package ru.nsu.kislitsyn.pizzeria;

import com.google.gson.annotations.Expose;

/**
 * A record of order suitable for using with Gson.
 */
public class Order {
    @Expose
    public int id;
    @Expose
    public String order;
}
