package ru.nsu.kislitsyn;

import java.util.ArrayList;

public class GraphAdjMatrix<T> implements Graph<T> {

    ArrayList<AdjMatrixLine<T>> vertices;

    private record AdjMatrixLCell<T> (T value, int weight){}

    private static class EdgeRecord<T> implements Edge<T> {
        T from;
        T to;
        int weight;

        EdgeRecord(T from, T to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
    private static class AdjMatrixLine<T> implements Vertice<T> {
        T value;
        ArrayList<AdjMatrixLCell<T>> line;

        public AdjMatrixLine(T value) {
            this.value = value;
            this.line = new ArrayList<>();
        }
    }

    public GraphAdjMatrix() {
        this.vertices = new ArrayList<>();
    }

    public Vertice<T> addVertice(T value) {
        AdjMatrixLine<T> newVertice = new AdjMatrixLine<>(value);

        for (AdjMatrixLine<T> line : this.vertices) {
            line.line.add(new AdjMatrixLCell<>(value, Integer.MAX_VALUE));
            newVertice.line.add(new AdjMatrixLCell<>(line.value, Integer.MAX_VALUE));
        }
        newVertice.line.add(new AdjMatrixLCell<>(newVertice.value, 0));

        this.vertices.add(newVertice);

        return newVertice;
    }

    public void deleteVertice(Vertice<T> verticeToDelete) {
        AdjMatrixLine<T> toDelete = (AdjMatrixLine<T>) verticeToDelete;
        this.vertices.removeIf((AdjMatrixLine<T> line) -> line.value == toDelete.value);

        for (AdjMatrixLine<T> vertice : this.vertices) {
            vertice.line.removeIf((AdjMatrixLCell<T> cell) -> cell.value == toDelete.value);
        }
    }

    public Edge<T> addEdge(Vertice<T> from, Vertice<T> to, int weight) {
//        EdgeRecord<T> edge = (EdgeRecord<T>) edgeToAdd;
        AdjMatrixLine<T> fromLine = (AdjMatrixLine<T>) from;
        AdjMatrixLine<T> toLine = (AdjMatrixLine<T>) to;
        AdjMatrixLCell<T> cell = new AdjMatrixLCell<>(toLine.value, weight);

        for (AdjMatrixLine<T> vertice : this.vertices) {
            if (vertice.value == fromLine.value) {
                for (int i = 0; i < vertice.line.size(); i++) {
                    if (vertice.line.get(i).value == toLine.value) {
                        vertice.line.set(i, cell);
                        break;
                    }
                }
            }
        }
        return null;
    }

    public void setVertice(Vertice<T> verticeToChange, T value) {
        AdjMatrixLine<T> toChange = (AdjMatrixLine<T>) verticeToChange;

        for (AdjMatrixLine<T> vertice : this.vertices) {
            if (vertice.value == toChange.value) {
                vertice.value = value;
            }
            for (AdjMatrixLCell<T> cell : vertice.line) {
                if (cell.value == toChange.value) {
//                    cell.value = value;
                    cell = new AdjMatrixLCell<>(value, cell.weight());
                }
            }
        }
    }

    public Vertice<T> getVertice(T value) {
        for (AdjMatrixLine<T> vertice : this.vertices) {
            if (vertice.value == value) {
                return vertice;
            }
        }
        return null;
    }

    public void setEdge(Edge<T> edgeToChange, int weight) {
        EdgeRecord<T> toChange = (EdgeRecord<T>) edgeToChange;
        for (AdjMatrixLine<T> vertice : this.vertices) {
            if (vertice.value == toChange.from) {
                for (AdjMatrixLCell<T> cell : vertice.line) {
                    if (cell.value == toChange.to) {
//                        cell.weight = weight;
                        cell = new AdjMatrixLCell<>(toChange.to, weight);
                    }
                }
            }
        }
    }

    public void deleteEdge(Edge<T> edgeToDelete) {
        this.setEdge(edgeToDelete, Integer.MAX_VALUE);
    }



}
