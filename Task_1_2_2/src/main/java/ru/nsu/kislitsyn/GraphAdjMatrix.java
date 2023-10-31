package ru.nsu.kislitsyn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
* Implementation of graph in matrix of adjacency.
*
* @param <T> parameter of type.
*/
public class GraphAdjMatrix<T> implements Graph<T> {

    private final ArrayList<AdjMatrixLine<T>> vertices;
    private int [][] dijkstraMx;

    /**
    * Getter of vertices.
    *
    * @return this.vertices.
    */
    public ArrayList<AdjMatrixLine<T>> getVertices() {
        return vertices;
    }


    public record AdjMatrixLine<T> (Vertice<T> value,
                                    ArrayList<Edge<T>> line,
                                    int distance, AdjMatrixLine<T> predecessor) {}

    /**
    * Constructor.
    */
    public GraphAdjMatrix() {
        this.vertices = new ArrayList<>();
    }

    /**
    * Adds vertice to the graph.
    *
    * @param value the value to add.
    *
    * @return the vertice with new value.
    */
    public Vertice<T> addVertice(T value) {
        AdjMatrixLine<T> newVerticeLine = new AdjMatrixLine<>(new Vertice<>(value),
                new ArrayList<>(), Integer.MAX_VALUE, null);
        Vertice<T> newVertice = new Vertice<>(value);
        for (AdjMatrixLine<T> vertice : this.vertices) {
            vertice.line.add(new Edge<>(vertice.value, newVertice, Integer.MAX_VALUE / 2));
            newVerticeLine.line.add(new Edge<>(newVertice, vertice.value(),
                    Integer.MAX_VALUE / 2));
        }
        newVerticeLine.line.add(new Edge<>(newVertice, newVertice, Integer.MAX_VALUE / 2));

        this.vertices.add(newVerticeLine);

        return newVertice;
    }

    /**
    * Deletes vertice from graph.
    *
    * @param verticeToDelete vertice we want tot delete.
    */
    public void deleteVertice(Vertice<T> verticeToDelete) {
        this.vertices.removeIf((AdjMatrixLine<T> line) -> line.value.equals(verticeToDelete.value()));

        for (AdjMatrixLine<T> vertice : this.vertices) {
            vertice.line.removeIf((Edge<T> edge) -> edge.to().equals(verticeToDelete));
        }
    }

    /**
    * Adds edge to the graph.
    *
    * @param edgeToAdd edge we need to add.
    *
    * @return the edge we added.
    */
    public Edge<T> addEdge(Edge<T> edgeToAdd) {
        int indexTo = -1;
        int indexFrom = -1;
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

    /**
    * Sets the value of vertice.
    *
    * @param verticeToChange the vertice we want to update.
    * @param value new value for vertice.
    */
    public void setVertice(Vertice<T> verticeToChange, T value) {
        Vertice<T> newVertice = new Vertice<>(value);
        for (AdjMatrixLine<T> vertice : this.vertices) {
            if (vertice.value.equals(verticeToChange.value())) {
                vertice = new AdjMatrixLine<>(new Vertice<>(value),
                        vertice.line, Integer.MAX_VALUE, null);
            }
            for (Edge<T> edge : vertice.line) {
                if (edge.to() == verticeToChange.value()) {
                    edge = new Edge<>(newVertice, edge.to(), edge.weight());
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
        if (index < this.vertices.size()) {
            return this.vertices.get(index).value;
        }
        return null;
    }

    /**
    * Setter for edge weight.
    *
    * @param edgeToChange the edge to change.
    * @param weight new weight of edge.
    */
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

    /**
    * Deletes edge.
    *
    * @param edgeToDelete edge to delete.
    */
    public void deleteEdge(Edge<T> edgeToDelete) {
        this.setEdge(edgeToDelete, Integer.MAX_VALUE);
    }


    /**
    * Builds graph out of file data.
    *
    * @param fileName name of file with graph data.
    *
    * @throws FileNotFoundException exception if there is no file.
    */
    @SuppressWarnings("unchecked")
    public void readFile(String fileName) throws FileNotFoundException {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            int verticeCount = 0;
            if (scanner.hasNextInt()) {
                verticeCount = scanner.nextInt();
            }
            for (int i = 0; i < verticeCount; i++) {
                if (scanner.hasNext()) {
                    this.addVertice((T) scanner.next());
                }
            }
            for (int i = 0; i < verticeCount; i++) {
                for (int j = 0; j < verticeCount; j++) {
                    if (scanner.hasNextInt()) {
                        this.addEdge(new Edge<>(this.getVertice(i), this.getVertice(j), scanner.nextInt()));
                    } else {
                        if (scanner.hasNext()) {
                            scanner.next();
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
