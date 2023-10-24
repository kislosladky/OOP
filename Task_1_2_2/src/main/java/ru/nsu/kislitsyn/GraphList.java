package ru.nsu.kislitsyn;

import java.util.ArrayList;
import java.util.List;

public class GraphList<T> implements Graph<T> {
    private List<VerticeList<T>> vertices;

    public GraphList() {
        this.vertices = new ArrayList<>();
    }

    public List<VerticeList<T>> getVertices() {
        return this.vertices;
    }
    public class VerticeList<T> implements Vertice<T> {
        T value;
        ArrayList<Vertice<T>> incidentVertices;

        private VerticeList(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }
    }

    private class EdgeList<T> implements Edge<T> {
        T from;
        T to;
        int weight;
    }

    private class VerticeInList<T> implements Vertice<T>{
        T value;
        int weight;
    }

    public Vertice<T> addVertice(T value) {
        VerticeList<T> newVertice = new VerticeList<T>(value);
        this.vertices.add(newVertice);
        return newVertice;
    }

    public void deleteVertice(Vertice<T> verticeToDelete) {
        vertices.remove(verticeToDelete);
        for (var vertice : this.vertices) {
            ((VerticeList<T>) vertice).incidentVertices.remove(verticeToDelete);
        }
    }

    public Edge<T> addEdge(Vertice<T> from, Vertice<T> to, int weight) {
        VerticeInList<T> fromVertice = (VerticeInList<T>) from;
        VerticeInList<T> toVertice = (VerticeInList<T>) to;
        toVertice.weight = weight;

        for (VerticeList<T> vertice : this.vertices) {
            if (vertice.value ==  fromVertice.value) {
                vertice.incidentVertices.add(to);
            }
        }
        return null;
    }

    public void setVertice(Vertice<T> verticeToChange, T value) {
        VerticeList<T> verticeListToChange = (VerticeList<T>) verticeToChange;
        for (VerticeList<T> vert : this.vertices) {
            if (vert.value == verticeListToChange.value) {
                vert.value = value;
                break;
            }
        }
    }

    public Vertice<T> getVertice(T value) {
        for (VerticeList<T> vertice : this.vertices) {
            if (vertice.value == value) {
                return vertice;
            }
        }
        return null;
    }



    public void setEdge(Edge<T> edgeToChange, int weight) {
        EdgeList<T> edge = (EdgeList<T>) edgeToChange;

        for (VerticeList<T> vertice : this.vertices) {
            for (Vertice<T> cell : vertice.incidentVertices) {
                if (((VerticeInList<T>) cell).weight == edge.weight) {
                    ((VerticeInList<T>) cell).weight = weight;
                }
                break;
            }
        }
    }



    public void deleteEdge(Edge<T> edgeToDelete) {
        EdgeList<T> edge = (EdgeList<T>) edgeToDelete;

        for (VerticeList<T> vertice : this.vertices) {
            for (Vertice<T> cell : vertice.incidentVertices) {
                if (((VerticeInList<T>) cell).value == edge.to) {
                    vertice.incidentVertices.remove(cell);
                }
                break;
            }
        }
    }
}

