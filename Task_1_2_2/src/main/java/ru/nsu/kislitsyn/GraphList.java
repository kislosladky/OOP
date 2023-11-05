package ru.nsu.kislitsyn;


import java.util.*;


/**
* Implementation of graph in list of incidence.
*
* @param <T> parameter of type.
*/
public class GraphList<T> extends Graph<T> {
    private final List<VerticeList<T>> vertices;

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
    public List<VerticeList<T>> getVertices() {
        return this.vertices;
    }

    /**
     * Record class to store vertice and it's incident vertices.
     *
     * @param value value of vertice.
     * @param incidentVertices list of the incident vertices.
     * @param distance lentgh of the path from source to this vertice.
     * @param predecessor predecessor of this vertice in the path.
     * @param <T> parameter of the type of value we store.
     */
    public record VerticeList<T>(Vertex<T> value, ArrayList<Edge<T>> incidentVertices,
                                 int distance, VerticeList<T> predecessor) {
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
        VerticeList<T> newVertice = new VerticeList<T>(new Vertex<>(value), new ArrayList<>(),
                Integer.MAX_VALUE / 2, null);
        this.vertices.add(newVertice);
        return newValue;
    }

    /**
    * Deletes vertice from graph.
    *
    * @param vertexToDelete vertice we want tot delete.
    */
    public void deleteVertex(Vertex<T> vertexToDelete) {
        vertices.removeIf((VerticeList<T> vertice) -> vertice.value.equals(vertexToDelete));
        for (var vertice : this.vertices) {
            ((VerticeList<T>) vertice).incidentVertices.remove(vertexToDelete);
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

        for (VerticeList<T> vertice : this.vertices) {
            if (vertice.value.equals(edgeToAdd.from())) {
                vertice.incidentVertices.add(edgeToAdd);
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
        for (VerticeList<T> vert : this.vertices) {
            if (vert.value().equals(vertexToChange)) {
                this.vertices.set(this.vertices.indexOf(vert),
                        new VerticeList<>(new Vertex<>(value),
                                vert.incidentVertices,
                                Integer.MAX_VALUE / 2, null));
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
            return this.vertices.get(index).value();
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

        for (VerticeList<T> vertice : this.vertices) {
            if (vertice.value.equals(edgeToChange.from())) {
                for (Edge<T> edge : vertice.incidentVertices) {
                    if (edge.equals(edgeToChange)) {
                        vertice.incidentVertices.set(vertice.incidentVertices.indexOf(edge),
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

    /**
    * Sets vertice's distance to MAX_VALUE and predecessor to null.
    *
    * @param vertice vertice to reset.
    */
    private void resetVertice(VerticeList<T> vertice) {
        this.vertices.set(this.vertices.indexOf(vertice),
                new VerticeList<>(vertice.value(), vertice.incidentVertices(),
                Integer.MAX_VALUE / 2, null));
    }

    /**
    * This function prepares graph to start dijkstra algorithm.
    *
    * @param from the source of the pathes.
    */
    private void dijkstraInit(VerticeList<T> from) {
        for (VerticeList<T> vertice : this.vertices) {
            this.resetVertice(vertice);
        }
        this.vertices.set(this.vertices.indexOf(from),
                new VerticeList<>(from.value(), from.incidentVertices(), 0, null));
    }

    /**
    * Implementation of relaxation for dijkstra's algorithm.
    *
    * @param from vertice.
    * @param to vertice.
    */
    private void relax(VerticeList<T> from, VerticeList<T> to) {
        for (Edge<T> edge : from.incidentVertices()) {
            if (edge.to().equals(to.value)) {
                if (to.distance > from.distance + edge.weight()) {
                    this.vertices.set(this.vertices.indexOf(to), new VerticeList<>(
                            to.value(), to.incidentVertices(),
                            from.distance + edge.weight(), from));
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
    private VerticeList<T> extractMin(ArrayDeque<VerticeList<T>> deque) {
        VerticeList<T> answ = deque.peek();
        for (VerticeList<T> i : deque) {
            if (i.distance() < answ.distance()) {
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
    private void dijkstra(VerticeList<T> from) {
        this.dijkstraInit(from);
        ArrayDeque<VerticeList<T>> deque = new ArrayDeque<>();
        HashSet<VerticeList<T>> set = new HashSet<>();
        deque.add(from);
        while (!deque.isEmpty()) {
            VerticeList<T> u = this.extractMin(deque);
            set.add(u);
            for (Edge<T> edge : u.incidentVertices()) {
                for (VerticeList<T> vertice : this.vertices) {
                    if (!set.contains(vertice)) {
                        deque.add(vertice);
                    }
                    if (edge.to().equals(vertice.value())) {
                        this.relax(u, vertice);
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
        VerticeList<T> from = null;
        for (VerticeList<T> vertice : this.vertices) {
            if (vertice.value().value().equals(fromValue)) {
                from = vertice;
                break;
            }

        }

        this.dijkstra(from);
        this.vertices.sort(Comparator.comparingInt(vertice -> vertice.distance));

        System.out.print("[");
        for (VerticeList<T> vertice : this.vertices) {
            System.out.print(vertice.value().value() + "(" + vertice.distance() + "), ");
        }
        System.out.print("]");
    }

}

