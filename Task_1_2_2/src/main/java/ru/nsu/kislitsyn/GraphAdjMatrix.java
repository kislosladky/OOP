package ru.nsu.kislitsyn;

import java.util.ArrayList;

public class GraphAdjMatrix<T> implements Graph<T> {

    private final ArrayList<AdjMatrixLine<T>> vertices;

    public ArrayList<AdjMatrixLine<T>> getVertices() {
        return vertices;
    }


    public record AdjMatrixLine<T> (T value, ArrayList<Edge<T>> line) {}

    public GraphAdjMatrix() {
        this.vertices = new ArrayList<>();
    }

    public Vertice<T> addVertice(T value) {
        AdjMatrixLine<T> newVerticeLine = new AdjMatrixLine<>(value, new ArrayList<>());
        Vertice<T> newVertice = new Vertice<>(value);
        for (AdjMatrixLine<T> vertice : this.vertices) {
            vertice.line.add(new Edge<>(new Vertice<>(vertice.value()), newVertice, Integer.MAX_VALUE));
            newVerticeLine.line.add(new Edge<>(newVertice, new Vertice<>(vertice.value()), Integer.MAX_VALUE));
        }
        newVerticeLine.line.add(new Edge<>(newVertice, newVertice, 0));

        this.vertices.add(newVerticeLine);

        return newVertice;
    }

    public void deleteVertice(Vertice<T> verticeToDelete) {
        this.vertices.removeIf((AdjMatrixLine<T> line) -> line.value.equals(verticeToDelete.value()));

        for (AdjMatrixLine<T> vertice : this.vertices) {
            vertice.line.removeIf((Edge<T> edge) -> edge.to().equals(verticeToDelete));
        }
    }

    public Edge<T> addEdge(Edge<T> edgeToAdd) {
        int indexTo = -1, indexFrom = -1;
        for (int i = 0; i < this.vertices.size(); i++) {
            if (this.vertices.get(i).value.equals(edgeToAdd.to())) {
                indexTo = i;
            }
            if (this.vertices.get(i).value.equals(edgeToAdd.from())) {
                indexFrom = i;
            }
        }

        this.vertices.get(indexFrom).line.set(indexTo, edgeToAdd);

        return edgeToAdd;
    }


    public void setVertice(Vertice<T> verticeToChange, T value) {
        Vertice<T> newVertice = new Vertice<>(value);
        for (AdjMatrixLine<T> vertice : this.vertices) {
            if (vertice.value.equals(verticeToChange.value())) {
                vertice = new AdjMatrixLine<>(value, vertice.line);
            }
            for (Edge<T> edge : vertice.line) {
                if (edge.to() == verticeToChange.value()) {
                    edge = new Edge<>(newVertice, edge.to(), edge.weight());
                }
            }
        }
    }

    public Vertice<T> getVertice(T value) {
        for (AdjMatrixLine<T> vertice : this.vertices) {
            if (vertice.value == value) {
                return new Vertice<>(vertice.value());
            }
        }
        return null;
    }

    public void setEdge(Edge<T> edgeToChange, int weight) {
        for (AdjMatrixLine<T> vertice : this.vertices) {
            if (vertice.value == edgeToChange.from()) {
                for (Edge<T> edge : vertice.line) {
                    if (edge.to().equals(edgeToChange.to())) {
                        edge = new Edge<>(edgeToChange.from(), edgeToChange.to(), weight);
                    }
                }
            }
        }
    }

    public void deleteEdge(Edge<T> edgeToDelete) {
        this.setEdge(edgeToDelete, Integer.MAX_VALUE);
    }
}
