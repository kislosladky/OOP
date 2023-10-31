package ru.nsu.kislitsyn;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
* Implementation of graph in matrix if incidence.
*
* @param <T> parameter of type.
*/
public class GraphIncMatrix<T> implements Graph<T> {

    ArrayList<IncMatrixLine<T>> lines;

    /**
    * Constructor.
    */
    public GraphIncMatrix() {
        this.lines = new ArrayList<>();
        //here I am adding an empty edge to have ability to add vertice into empty graph
        this.lines.add(new IncMatrixLine<>(new Edge<>(null, null,0), new ArrayList<>()));

    }

    /**
    * Getter of vertices.
    *
    * @return this.vertices.
    */
    public ArrayList<Cell<T>> getVertices() {
        if (this.lines.isEmpty()) {
            return null;
        }
        return this.lines.get(0).vertices;
    }

    public record Cell<T> (Vertice<T> thisVertice, Vertice<T> incVertice, int incident) {}
    public record IncMatrixLine<T> (Edge<T> edge, ArrayList<Cell<T>> vertices) {}

    /**
    * Adds vertice to the graph.
    *
    * @param value the value to add.
    *
    * @return the vertice with new value.
    */
    public Vertice<T> addVertice(T value) {
        Vertice<T> newVertice = new Vertice<>(value);
//        Cell<T> newCell = new Cell<>(newVertice, 0);
        for (IncMatrixLine<T> line : this.lines) {
            line.vertices.add(new Cell<>(line.edge.from(), newVertice, 0));
        }
        return newVertice;
    }

    /**
    * Deletes vertice from graph.
    *
    * @param verticeToDelete vertice we want tot delete.
    */
    public void deleteVertice(Vertice<T> verticeToDelete) {
        this.lines.removeIf((IncMatrixLine<T> line) ->
                line.edge.to() != null &&
                (line.edge().to().equals(verticeToDelete) ||
                line.edge().from().equals(verticeToDelete)));
        for (IncMatrixLine<T> line : this.lines) {
            line.vertices.removeIf((Cell<T> vertice) ->
                    vertice.incVertice().equals(verticeToDelete));
        }
    }

    /**
    * Sets the value of vertice
    *
    * @param verticeToChange the vertice we want to update.
    * @param value new value for vertice.
    */
    public void setVertice(Vertice<T> verticeToChange, T value) {
        Vertice<T> newVertice = new Vertice<>(value);
        for (IncMatrixLine<T> line : this.lines) {
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
                    cell = new Cell<>(line.edge.from(), new Vertice<>(value), cell.incident);
                }
            }
        }
    }

    /**
    * Getter for vertice.
    *
    * @param index the index of the vertice we need.
    *
    * @return the vertice with this value.
    */
    public Vertice<T> getVertice(int index) {
        if (this.lines.isEmpty()) {
            return null;
        } else {
            return this.lines.get(0).vertices.get(index).thisVertice;
        }
    }

    /**
    * Adds edge to the graph.
    *
    * @param edgeToAdd edge we need to add.
    *
    * @return the edge we added.
    */
    @SuppressWarnings("unchecked")
    public Edge<T> addEdge(Edge<T> edgeToAdd) {
        IncMatrixLine<T> toAdd = new IncMatrixLine<T>(edgeToAdd,
                (ArrayList<Cell<T>>) this.lines.get(0).vertices.clone());
        for (Cell<T> vertice : toAdd.vertices) {
            if (vertice.incVertice().equals(edgeToAdd.from())) {
                vertice = new Cell<>(vertice.thisVertice, vertice.incVertice(), 1);
            } else if (vertice.incVertice.equals(edgeToAdd.to())) {
                vertice = new Cell<>(vertice.thisVertice, vertice.incVertice(), -1);
            }
        }
        this.lines.add(toAdd);
        return edgeToAdd;
    }

    /**
    * Deletes edge.
    *
    * @param edgeToDelete edge to delete.
    */
    public void deleteEdge(Edge<T> edgeToDelete) {

        this.lines.removeIf((IncMatrixLine<T> line) ->
                ((line.edge().weight() == edgeToDelete.weight())
                        && (line.edge().to() == edgeToDelete.to())
                        && (line.edge().from() == edgeToDelete.from())));

    }

    /**
    * Setter for edge weight.
    *
    * @param edgeToChange the edge to change.
    * @param weight new weight of edge.
    */
    public void setEdge(Edge<T> edgeToChange, int weight) {
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
