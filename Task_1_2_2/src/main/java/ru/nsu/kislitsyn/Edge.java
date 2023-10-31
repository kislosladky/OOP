package ru.nsu.kislitsyn;

/**
* Record class to store edge.
*
* @param from vertice from.
* @param to vertice to.
* @param weight weight of the edge.
* @param <T> parameter of the class.
*/
public record Edge<T>(Vertice<T> from, Vertice<T> to, int weight) {}
