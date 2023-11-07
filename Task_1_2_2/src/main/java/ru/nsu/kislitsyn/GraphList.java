package ru.nsu.kislitsyn;


import java.util.List;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Comparator;

/**
* Implementation of graph in list of incidence.
*
* @param <T> parameter of type.
*/
public class GraphList<T> extends Graph<T> {
    private final int MAX_DISTANCE = Integer.MAX_VALUE / 2;
    private final List<VertexList> vertices;

    /**
    * Constructor.
    */
    public GraphList() {
        this.vertices = new ArrayList<>();
    }

    /**
    * Getter of vertices.
    *
    * @return this.vertices.
    */
    public List<VertexList> getVertices() {
        return this.vertices;
    }

    /**
     * Class to store vertice and it's incident vertices.
     */

    public class VertexList {
        private Vertex<T> value;
        private final ArrayList<Edge<T>> incidentVertices;
        private int distance;

        public VertexList(Vertex<T> value, ArrayList<Edge<T>> incidentVertices, int distance) {
            this.value = value;
            this.incidentVertices = incidentVertices;
            this.distance = distance;
        }
        public VertexList(Vertex<T> value, ArrayList<Edge<T>> incidentVertices) {
            this.value = value;
            this.incidentVertices = incidentVertices;
            this.distance = MAX_DISTANCE;
        }

        public VertexList(Vertex<T> value) {
            this.value = value;
            this.incidentVertices = new ArrayList<>();
            this.distance = MAX_DISTANCE;
        }

        public Vertex<T> getValue() {
            return value;
        }

        public void setValue(Vertex<T> value) {
            this.value = value;
        }

        public ArrayList<Edge<T>> getIncidentVertices() {
            return incidentVertices;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
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
        Vertex<T> newValue = new Vertex<>(value);
        VertexList newVertex = new VertexList(new Vertex<>(value));
        this.vertices.add(newVertex);
        return newValue;
    }

    /**
    * Deletes vertice from graph.
    *
    * @param vertexToDelete vertice we want tot delete.
    */
    public void deleteVertex(Vertex<T> vertexToDelete) {
        vertices.removeIf((VertexList vertex) -> vertex.value.equals(vertexToDelete));
        for (VertexList vertex : this.vertices) {
            vertex.incidentVertices.removeIf((Edge<T> edge) ->
                    edge.to().equals(vertexToDelete) || edge.from().equals(vertexToDelete));
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

        for (VertexList vertex : this.vertices) {
            if (vertex.value.equals(edgeToAdd.from())) {
                vertex.incidentVertices.add(edgeToAdd);
            }
        }
        return edgeToAdd;
    }

    /**
    * Sets the value of vertice.
    *
    * @param vertexToChange the vertice we want to update.
    * @param value new value for vertice.
    */
    public void setVertex(Vertex<T> vertexToChange, T value) {
        for (VertexList vert : this.vertices) {
            if (vert.getValue().equals(vertexToChange)) {
                this.vertices.get(this.vertices.indexOf(vert)).setValue(new Vertex<>(value));
                break;
            }
        }
    }

    /**
    * Getter for vertice.
    *
    * @param index the index of vertex we need.
    *
    * @return the vertice with this value.
    */
    public Vertex<T> getVertex(int index) {
        if (index < this.vertices.size()) {
            return this.vertices.get(index).getValue();
        } else {
            return null;
        }
    }


    public VertexList getFullVertex(int index) {
        if (index < this.vertices.size()) {
            return this.vertices.get(index);
        } else {
            return null;
        }
    }


    /**
    * Setter for edge weight.
    *
    * @param edgeToChange the edge to change.
    * @param weight new weight of edge.
    */
    public void setEdge(Edge<T> edgeToChange, int weight) {

        for (VertexList vertex : this.vertices) {
            if (vertex.value.equals(edgeToChange.from())) {
                for (Edge<T> edge : vertex.incidentVertices) {
                    if (edge.equals(edgeToChange)) {
                        vertex.incidentVertices.set(vertex.incidentVertices.indexOf(edge),
                                new Edge<>(edge.from(), edge.to(), weight));
                        break;
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

        for (VertexList vertex : this.vertices) {
            if (edgeToDelete.from().equals(vertex.getValue())) {
                for (Edge<T> edge : vertex.getIncidentVertices()) {
                    if (edgeToDelete.equals(edge)) {
                        vertex.getIncidentVertices().remove(edge);
                        break;
                    }
                }
            }
        }
    }

    /**
    * Sets vertice's distance to MAX_VALUE and predecessor to null.
    *
    * @param vertex vertice to reset.
    */
    private void resetVertice(VertexList vertex) {
        this.vertices.get(this.vertices.indexOf(vertex)).setDistance(MAX_DISTANCE);
    }

    /**
    * This function prepares graph to start dijkstra algorithm.
    *
    * @param from the source of the pathes.
    */
    private void dijkstraInit(VertexList from) {
        for (VertexList vertex : this.vertices) {
            this.resetVertice(vertex);
        }
//        this.vertices.set(this.vertices.indexOf(from),
//                new VertexList<>(from.alue(), from.incidentVertices(), 0));
        this.vertices.get(this.vertices.indexOf(from)).setDistance(0);
    }

    /**
    * Implementation of relaxation for dijkstra's algorithm.
    *
    * @param from vertex.
    * @param to vertex.
    */
    private void relax(VertexList from, VertexList to) {
        for (Edge<T> edge : from.getIncidentVertices()) {
            if (edge.to().equals(to.value)) {
                if (to.distance > from.distance + edge.weight()) {
                    this.vertices.get(this.vertices.indexOf(to))
                            .setDistance(from.distance + edge.weight());
                }
            }
        }
    }

    /**
    * Function for dijkstra's algorithm, that finds the vertice
    * from queue with the least distance from the source.
    *
    * @param deque queue of vertices.
    *
    * @return vertice with the lowest distance.
    */
    private VertexList extractMin(ArrayDeque<VertexList> deque) {
        VertexList answ = deque.peek();
        for (VertexList i : deque) {
            if (i.getDistance() < answ.getDistance()) {
                answ = i;
            }
        }
        deque.remove(answ);
        return answ;
    }

    /**
    * Implementation of dijkstra's algorithm.
    *
    * @param from the source of the path.
    */
    private void dijkstra(VertexList from) {
        this.dijkstraInit(from);
        ArrayDeque<VertexList> deque = new ArrayDeque<>();
        HashSet<VertexList> set = new HashSet<>();
        deque.add(from);
        while (!deque.isEmpty()) {
            VertexList u = this.extractMin(deque);
            set.add(u);
            for (Edge<T> edge : u.getIncidentVertices()) {
                for (VertexList vertex : this.vertices) {
                    if (!set.contains(vertex)) {
                        deque.add(vertex);
                    }
                    if (edge.to().equals(vertex.getValue())) {
                        this.relax(u, vertex);
                    }
                }
            }
        }
    }

    /**
    * Sorts and prints vertices in order length of path from source.
    *
    * @param fromValue source of the path.
    */
    public void sortWithPathLengthAndPrint(T fromValue) {
        VertexList from = null;
        for (VertexList vertex : this.vertices) {
            if (vertex.getValue().value().equals(fromValue)) {
                from = vertex;
                break;
            }

        }

        this.dijkstra(from);
        this.vertices.sort(Comparator.comparingInt(vertice -> vertice.distance));

        System.out.print("[");
        for (VertexList vertex : this.vertices) {
            System.out.print(vertex.getValue().value() + "(" + vertex.getDistance() + "), ");
        }
        System.out.print("]");
    }

}

