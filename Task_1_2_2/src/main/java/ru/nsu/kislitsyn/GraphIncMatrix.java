package ru.nsu.kislitsyn;


import java.util.*;

/**
 * Implementation of graph in matrix if incidence.
 *
 * @param <T> parameter of type.
 */
public class GraphIncMatrix<T> extends Graph<T> {

    private final Matrix<Edge<T>, IncMatrixVertex> matrix;

    private final Map<Vertex<T>, Integer> distances;

    /**
     * Constructor.
     */
    public GraphIncMatrix() {
        //here I am adding an empty edge to have ability to add vertice into empty graph
        matrix = new Matrix<>();
        this.matrix.addLine(new Edge<>(null, null, 0));
        this.distances = new HashMap<>();
    }

    public Matrix<Edge<T>, IncMatrixVertex> getMatrix() {
        return matrix;
    }

    public void setMatrix(List<Line<Edge<T>, IncMatrixVertex>> matrix) {
        this.matrix.setMatrix(matrix);
    }

    /**
     * Class for storing data in matrix.
     * Equals checks only values of the vertices, ignoring other fields of objects.
     */
    public class IncMatrixVertex {
        private Vertex<T> vertex;
        private Incidence incident;

        public IncMatrixVertex(Vertex<T> vertex) {
            this.vertex = vertex;
            this.incident = Incidence.NOT_INCIDENT;
        }

        public Vertex<T> getVertex() {
            return vertex;
        }

        public void setVertex(Vertex<T> vertex) {
            this.vertex = vertex;
        }

        public Incidence getIncident() {
            return incident;
        }

        public void setIncident(Incidence incident) {
            this.incident = incident;
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
            IncMatrixVertex that = (IncMatrixVertex) o;
            return Objects.equals(getVertex(), that.getVertex());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getVertex());
        }
    }


    /**
     * Adds vertice to the graph.
     *
     * @param value the value to add.
     *
     * @return the vertice with new value.
     */
    public Vertex<T> addVertex(T value) {
        Vertex<T> newVertex = new Vertex<>(value);
        this.matrix.addColumn(new IncMatrixVertex(newVertex));
        this.distances.put(newVertex, Integer.MAX_VALUE / 2);
        return newVertex;
    }

    /**
     * Deletes vertice from graph.
     *
     * @param vertexToDelete vertice we want tot delete.
     */
    public void deleteVertex(Vertex<T> vertexToDelete) {
        this.matrix.removeColumnByValue(new IncMatrixVertex(vertexToDelete));
    }

    /**
     * Sets the value of vertice.
     *
     * @param vertexToChange the vertice we want to update.
     * @param value new value for vertice.
     */
    public void setVertex(Vertex<T> vertexToChange, T value) {
        Vertex<T> newVertex = new Vertex<>(value);
        int index = this.matrix.getLine(0).indexOf(new IncMatrixVertex(vertexToChange));

        for (Line<Edge<T>, IncMatrixVertex> line : matrix.getMatrix()) {
            IncMatrixVertex newIncVertex = line.getColumns().get(index);
            newIncVertex.setVertex(newVertex);
            line.setColumn(index, newIncVertex);
        }
    }

    /**
     * Getter for vertice.
     *
     * @param index the index of the vertice we need.
     *
     * @return the vertice with this value.
     */
    public Vertex<T> getVertex(int index) {
        if (this.matrix.getMatrix().isEmpty()) {
            return null;
        } else {
            return this.matrix.getLine(0).get(index).getVertex();
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
        this.matrix.addLine(edgeToAdd);
        int index = this.matrix.getMatrix().size() - 1;
        for (IncMatrixVertex vertex : this.matrix.getLine(index)) {
            if (vertex.getVertex().equals(edgeToAdd.from())) {
                vertex.setIncident(Incidence.FROM);
            } else if (vertex.getVertex().equals(edgeToAdd.to())) {
                vertex.setIncident(Incidence.TO);
            }
        }
        return edgeToAdd;
    }

    /**
     * Deletes edge.
     *
     * @param edgeToDelete edge to delete.
     */
    public void deleteEdge(Edge<T> edgeToDelete) {
        this.matrix.removeLineByValue(edgeToDelete);
    }

    /**
     * Setter for edge weight.
     *
     * @param edgeToChange the edge to change.
     * @param weight new weight of edge.
     */
    public void setEdge(Edge<T> edgeToChange, int weight) {
        Edge<T> newEdge = new Edge<>(edgeToChange.from(), edgeToChange.to(), weight);
        int index = this.matrix.indexOfLine(edgeToChange);
        this.matrix.getMatrix().set(index, new Line<>(newEdge, this.matrix.getLine(index)));
    }

    public  Edge<T> getEdge(Vertex<T> from, Vertex<T> to) {
        for (Line<Edge<T>, IncMatrixVertex> line : this.matrix.getMatrix()) {
            try {
                if (line.getValue().from().equals(from) && line.getValue().to().equals(to)) {
                    return line.getValue();
                }
            } catch (NullPointerException e) {
                continue;
            }
        }
        return null;
    }

    List<Vertex<T>> sort() {
        List<Vertex<T>> toSort = new ArrayList<>(this.getMatrix().getLine(0).stream().map(IncMatrixVertex::getVertex).toList());

        toSort.sort(Comparator.comparingInt(vertex
                -> distances.getOrDefault(vertex, Integer.MAX_VALUE / 2)));
        return toSort;
    }

    List<Edge<T>> getIncidentEdges(Vertex<T> from) {
        List<Edge<T>> incident = new ArrayList<>();

        for (Line<Edge<T>, IncMatrixVertex> line : this.getMatrix().getMatrix()) {
            if (line.getValue().from() != null && line.getValue().from().equals(from)) {
                incident.add(line.getValue());
            }
        }

        return incident;
    }

    List<Vertex<T>> getAllVertices() {
        List<Vertex<T>> answ = new ArrayList<>();
        for (IncMatrixVertex incMatrixVertex : this.matrix.getLine(0)) {
            answ.add(incMatrixVertex.getVertex());
        }
        return answ;
    }

    int getDistance(Vertex<T> vertex) {
        return this.distances.get(vertex);
    }

    void setDistance(Vertex<T> from, int distance) {
        this.distances.put(from, distance);
    }

    public enum Incidence {
        FROM,
        TO,
        NOT_INCIDENT
    }
}