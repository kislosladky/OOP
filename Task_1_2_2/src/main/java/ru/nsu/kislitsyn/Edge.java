package ru.nsu.kislitsyn;

/**
* Record class to store edge.
*
* @param from vertice from.
* @param to vertice to.
* @param weight weight of the edge.
* @param <T> parameter of the class.
*/
public record Edge<T>(Vertex<T> from, Vertex<T> to, int weight) {}
