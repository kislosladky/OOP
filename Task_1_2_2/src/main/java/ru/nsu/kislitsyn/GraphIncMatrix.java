package ru.nsu.kislitsyn;


import java.util.ArrayList;

public class GraphIncMatrix<T> implements Graph<T> {

    ArrayList<IncMatrixLine<T>> lines;

    private class Cell<T> implements Vertice<T> {
        T incVertice;
        int incident;

        Cell(T incVertice, int incident) {
            this.incVertice = incVertice;
            this.incident = incident;
        }
    }

    private class IncMatrixLine<T> implements Edge<T> {
        int weight;
        Vertice<T> from;
        Vertice<T> to;
        ArrayList<Cell<T>> vertices;

        IncMatrixLine(Vertice<T> from, Vertice<T> to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    public Vertice<T> addVertice(T value) {
        Cell<T> newCell = new Cell<>(value, 0);
        for (IncMatrixLine<T> line : this.lines) {
            line.vertices.add(new Cell<>(value, 0));
        }
        return newCell;
    }

    public void deleteVertice(Vertice<T> verticeToDelete) {
        Cell<T> toDelete = (Cell<T>) verticeToDelete;
        for (IncMatrixLine<T> line : this.lines) {
            line.vertices.removeIf((Cell<T> vertice) -> vertice.incVertice == toDelete.incVertice);
        }
    }

    public void setVertice(Vertice<T> verticeToChange, T value) {
        Cell<T> toChange = (Cell<T>) verticeToChange;
        for (IncMatrixLine<T> line: this.lines) {
            for (Cell<T> cell : line.vertices) {
                if (cell.incVertice == toChange.incVertice) {
                    cell.incVertice = value;
                    break;
                }
            }
        }
    }
    public Vertice<T> getVertice(T value) {
        return null;
    }

    @SuppressWarnings("unchecked")
    public Edge<T> addEdge(Vertice<T> from, Vertice<T> to, int weight) {
        Cell<T> cellFrom = (Cell<T>) from;
        Cell<T> cellTo = (Cell<T>) to;
        IncMatrixLine<T> toAdd = new IncMatrixLine<T>(from, to, weight);
        toAdd.vertices = (ArrayList<Cell<T>>) this.lines.get(0).vertices.clone();
        for (Cell<T> cell : toAdd.vertices) {
            cell.incident = 0;
            if (cell.incVertice == cellFrom.incVertice) {
                cell.incident = 1;
            } else if (cell.incVertice == cellTo.incVertice) {
                cell.incident = -1;
            }
        }
        this.lines.add(toAdd);
        return toAdd;
    }
    public void deleteEdge(Edge<T> edgeToDelete) {
        IncMatrixLine<T> toDelete = (IncMatrixLine<T>) edgeToDelete;

        this.lines.removeIf((IncMatrixLine<T> line) -> ((line.weight == toDelete.weight) && (line.to == toDelete.to) && (line.from == toDelete.from)));

    }

    public void setEdge(Edge<T> edgeToChange, int weight) {
        IncMatrixLine<T> toChange = (IncMatrixLine<T>) edgeToChange;
        for (IncMatrixLine<T> line : this.lines) {
            if (line.from == toChange.to && line.to == toChange.to) {
                line.weight = weight;
                break;
            }
        }
    }

}
