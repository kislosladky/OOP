package ru.nsu.kislitsyn;

import java.util.ArrayList;
import java.util.List;

public class GraphList<T> implements Graph<T> {
    private final List<VerticeList<T>> vertices;

    public GraphList() {
        this.vertices = new ArrayList<>();
    }

    public List<VerticeList<T>> getVertices() {
        return this.vertices;
    }
    public record VerticeList<T> (Vertice<T> value, ArrayList<Edge<T>> incidentVertices)  {}

    public Vertice<T> addVertice(T value) {
        Vertice<T> newValue = new Vertice<>(value);
        VerticeList<T> newVertice = new VerticeList<T>(new Vertice<>(value), new ArrayList<>());
        this.vertices.add(newVertice);
        return newValue;
    }

    public void deleteVertice(Vertice<T> verticeToDelete) {
        vertices.removeIf((VerticeList<T> vertice) -> vertice.value.equals(verticeToDelete));
        for (var vertice : this.vertices) {
            ((VerticeList<T>) vertice).incidentVertices.remove(verticeToDelete);
        }
    }

    public Edge<T> addEdge(Edge<T> edgeToAdd) {

        for (VerticeList<T> vertice : this.vertices) {
            if (vertice.value ==  edgeToAdd.from().value()) {
                vertice.incidentVertices.add(edgeToAdd);
            }
        }
        return edgeToAdd;
    }

    public void setVertice(Vertice<T> verticeToChange, T value) {
//        VerticeList<T> verticeListToChange = (VerticeList<T>) verticeToChange;
        for (VerticeList<T> vert : this.vertices) {
            if (vert.value.value() == verticeToChange.value()) {
                vert = new VerticeList<>(new Vertice<>(value), vert.incidentVertices);
                break;
            }
        }
    }

    public Vertice<T> getVertice(T value) {
        for (VerticeList<T> vertice : this.vertices) {
            if (vertice.value == value) {
                return new Vertice<>(vertice.value.value());
            }
        }
        return null;
    }



    public void setEdge(Edge<T> edgeToChange, int weight) {

        for (VerticeList<T> vertice : this.vertices) {
            if (vertice.value.equals(edgeToChange.from())) {
                for (Edge<T> edge : vertice.incidentVertices) {
                    if (edge.to().equals(edgeToChange)) {
                        vertice.incidentVertices.set(vertice.incidentVertices.indexOf(edge), new Edge<>(edge.from(), edge.to(), weight));
                        break;
                    }
                }
            }
        }
    }



    public void deleteEdge(Edge<T> edgeToDelete) {

        for (VerticeList<T> vertice : this.vertices) {
            if (edgeToDelete.from().equals(vertice)) {
                for (Edge<T> edge : vertice.incidentVertices()) {
                    if (edgeToDelete.equals(edge)) {
                        vertice.incidentVertices().remove(edge);
                        break;
                    }
                }
            }
        }
    }
}

