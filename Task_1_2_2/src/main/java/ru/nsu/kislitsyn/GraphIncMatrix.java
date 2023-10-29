package ru.nsu.kislitsyn;


import java.util.ArrayList;

public class GraphIncMatrix<T> implements Graph<T> {

    ArrayList<IncMatrixLine<T>> lines;

    public GraphIncMatrix() {
        this.lines = new ArrayList<>();
        //here I am adding an empty edge to have ability to add vertice into empty graph
        this.lines.add(new IncMatrixLine<>(new Edge<>(null, null,0), new ArrayList<>()));

    }
    public ArrayList<Cell<T>> getVertices() {
        if (this.lines.isEmpty()) {
            return null;
        }
        return this.lines.get(0).vertices;
    }

    public record Cell<T> (Vertice<T> incVertice, int incident) {}
    public record IncMatrixLine<T> (Edge<T> edge, ArrayList<Cell<T>> vertices) {}

    public Vertice<T> addVertice(T value) {
        Vertice<T> newVertice = new Vertice<>(value);
        Cell<T> newCell = new Cell<>(newVertice, 0);
        for (IncMatrixLine<T> line : this.lines) {
            line.vertices.add(newCell);
        }
        return newVertice;
    }

    public void deleteVertice(Vertice<T> verticeToDelete) {
//        Cell<T> toDelete = (Cell<T>) verticeToDelete;
        this.lines.removeIf((IncMatrixLine<T> line) ->
                line.edge.to() != null &&
                (line.edge().to().equals(verticeToDelete) ||
                line.edge().from().equals(verticeToDelete)));
        for (IncMatrixLine<T> line : this.lines) {
            line.vertices.removeIf((Cell<T> vertice) ->
                    vertice.incVertice().equals(verticeToDelete));
        }
    }

    public void setVertice(Vertice<T> verticeToChange, T value) {
//        Cell<T> toChange = (Cell<T>) verticeToChange;
        Vertice<T> newVertice = new Vertice<>(value);
        for (IncMatrixLine<T> line: this.lines) {
            if (line.edge().to().equals(verticeToChange)) {
                line = new IncMatrixLine<>(
                        new Edge<>(line.edge.from(), newVertice, line.edge.weight()),
                        line.vertices);
            }

            if (line.edge().from().equals(verticeToChange)) {
                line = new IncMatrixLine<>(
                        new Edge<>(newVertice, line.edge.to(), line.edge.weight()),
                        line.vertices);
            }
            for (Cell<T> cell : line.vertices) {
                if (cell.incVertice().equals(verticeToChange)) {
                    cell = new Cell<>(new Vertice<>(value), cell.incident);
                }
            }
        }
    }
    public Vertice<T> getVertice(T value) {
        return null;
    }

    @SuppressWarnings("unchecked")
    public Edge<T> addEdge(Edge<T> edgeToAdd) {
        IncMatrixLine<T> toAdd = new IncMatrixLine<T>(edgeToAdd,
                (ArrayList<Cell<T>>) this.lines.get(0).vertices.clone());
        for (Cell<T> vertice : toAdd.vertices) {
            if (vertice.incVertice().equals(edgeToAdd.from())) {
                vertice = new Cell<>(vertice.incVertice(), 1);
            } else if (vertice.incVertice.equals(edgeToAdd.to())) {
                vertice = new Cell<>(vertice.incVertice(), -1);
            }
        }
        this.lines.add(toAdd);
        return edgeToAdd;
    }
    public void deleteEdge(Edge<T> edgeToDelete) {

        this.lines.removeIf((IncMatrixLine<T> line) ->
                ((line.edge().weight() == edgeToDelete.weight())
                        && (line.edge().to() == edgeToDelete.to())
                        && (line.edge().from() == edgeToDelete.from())));

    }

    public void setEdge(Edge<T> edgeToChange, int weight) {
//        IncMatrixLine<T> toChange = (IncMatrixLine<T>) edgeToChange;
        for (IncMatrixLine<T> line : this.lines) {
            if (line.edge().equals(edgeToChange)) {
                line = new IncMatrixLine<>(
                        new Edge<>(edgeToChange.from(), edgeToChange.to(), weight),
                        line.vertices);
                break;

            }
        }
    }

}
