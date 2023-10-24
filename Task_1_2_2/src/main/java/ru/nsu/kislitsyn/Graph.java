package ru.nsu.kislitsyn;

public interface Graph<T> {
    Vertice<T> addVertice(T value);
    void deleteVertice(Vertice<T> verticeToDelete);
    void setVertice(Vertice<T> verticeToChange, T value);
    Vertice<T> getVertice(T value);

    Edge<T> addEdge(Vertice<T> from, Vertice<T> to, int weight);
    void deleteEdge(Edge<T> edgeToDelete);
    void setEdge(Edge<T> edgeToChange, int weight);
}
