package ru.nsu.kislitsyn;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Implementation of graph in matrix of adjacency.
 *
 * @param <T> parameter of type.
 */
public class GraphAdjMatrix<T> extends Graph<T> {

    private Matrix<Vertex<T>, AdjMatrixVertex> matrix;

    /**
     * Getter of vertices.
     *
     * @return this.vertices.
     */
    public ArrayList<AdjMatrixVertex> getVertices() {
        return matrix.getLine(0);
    }



    public Matrix<Vertex<T>, AdjMatrixVertex> getMatrix() {
        return this.matrix;
    }

    /**
     * Class of vertex in matrix.
     *
     */
    public class AdjMatrixVertex {
        private Vertex<T> value;
        private int weight;
        private int distance;

        public AdjMatrixVertex(T value) {
            this.value = new Vertex<>(value);
            this.weight = -1;
            this.distance = Integer.MAX_VALUE / 2;
        }

        public Vertex<T> getValue() {
            return value;
        }

        public void setValue(Vertex<T> value) {
            this.value = value;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o.getClass() != this.getClass()) {
                return false;
            }
            AdjMatrixVertex that = (AdjMatrixVertex) o;
            return Objects.equals(this.getValue(), that.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getValue(), getWeight());
        }
    }

    /**
     * Constructor.
     */
    public GraphAdjMatrix() {
        this.matrix = new Matrix<>();
    }

    /**
     * Adds vertice to the graph.
     *
     * @param value the value to add.
     *
     * @return the vertice with new value.
     */
    public Vertex<T> addVertex(T value) {
        this.matrix.addLine(new Vertex<>(value));
        this.matrix.addColumn(new AdjMatrixVertex(value));

        return null;
    }

    /**
     * Deletes vertice from graph.
     *
     * @param vertexToDelete vertice we want tot delete.
     */
    public void deleteVertex(Vertex<T> vertexToDelete) {
        AdjMatrixVertex toDelete = new AdjMatrixVertex(vertexToDelete.value());
        this.matrix.removeColumnByValue(toDelete);
        this.matrix.removeLineByValue(toDelete.getValue());
    }

    /**
     * Adds edge to the graph.
     *
     * @param edgeToAdd edge we need to add.
     *
     * @return the edge we added.
     */
    public Edge<T> addEdge(Edge<T> edgeToAdd) {
        int indexTo = matrix.indexOfLine(edgeToAdd.to());
        int indexFrom = matrix.indexOfLine(edgeToAdd.from());

        this.matrix.getLine(indexFrom).get(indexTo).setWeight(edgeToAdd.weight());

        return edgeToAdd;
    }

    /**
     * Sets the value of vertice.
     *
     * @param vertexToChange the vertice we want to update.
     * @param value new value for vertice.
     */
    public void setVertex(Vertex<T> vertexToChange, T value) {
        Vertex<T> newVertex = new Vertex<>(value);
        int index = matrix.indexOfLine(vertexToChange);

        for (Line<Vertex<T>, AdjMatrixVertex> line : this.matrix.getMatrix()) {
            AdjMatrixVertex vertex = line.getColumns().get(index);
            vertex.setValue(newVertex);
            line.setColumn(index, vertex);
        }
        matrix.getLineValue(index).setValue(newVertex);
    }

    /**
     * Getter for vertice.
     *
     * @param index the index of the vertice we need.
     *
     * @return the vertice with this value.
     */
    public Vertex<T> getVertex(int index) {
        if (index < this.matrix.getMatrix().size()) {
            return this.matrix.getLineValue(index).getValue();
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
        Edge<T> newEdge = new Edge<>(edgeToChange.from(), edgeToChange.to(), weight);
        addEdge(newEdge);
    }

    /**
     * Deletes edge.
     *
     * @param edgeToDelete edge to delete.
     */
    public void deleteEdge(Edge<T> edgeToDelete) {
        this.setEdge(edgeToDelete, -1);
    }

    public Edge<T> getEdge(Vertex<T> from, Vertex<T> to) {
        int weight = matrix.getColumnByIndex(matrix.indexOfLine(from), matrix.indexOfLine(to)).getWeight();
        return new Edge<>(from, to, weight);
    }
}