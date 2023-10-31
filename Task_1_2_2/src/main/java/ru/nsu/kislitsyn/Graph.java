package ru.nsu.kislitsyn;

import java.io.FileNotFoundException;

public interface Graph<T> {
    Vertice<T> addVertice(T value);
    void deleteVertice(Vertice<T> verticeToDelete);
    void setVertice(Vertice<T> verticeToChange, T value);
    Vertice<T> getVertice(int index);

    Edge<T> addEdge(Edge<T> edgeToAd);
    void deleteEdge(Edge<T> edgeToDelete);
    void setEdge(Edge<T> edgeToChange, int weight);
}
