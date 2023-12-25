package ru.nsu.kislitsyn;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
* Interface for the graph.
*
* @param <T> parameter of type of value we store in vertices.
*/
public abstract class Graph<T> {
    public abstract Vertex<T> addVertex(T value);

    public abstract void deleteVertex(Vertex<T> vertexToDelete);

    public abstract void setVertex(Vertex<T> vertexToChange, T value);

    public abstract Vertex<T> getVertex(int index);

    public abstract Edge<T> addEdge(Edge<T> edgeToAd);

    public abstract void deleteEdge(Edge<T> edgeToDelete);

    public abstract void setEdge(Edge<T> edgeToChange, int weight);

    public abstract Edge<T> getEdge(Vertex<T> from, Vertex<T> to);

    abstract List<Vertex<T>> sort();

    private void dijkstra(T fromValue) {
        Vertex<T> vertexFrom = new Vertex<>(fromValue);
        this.initDistance(vertexFrom);

        Deque<Vertex<T>> deque = new ArrayDeque<>();
        Set<Vertex<T>> set = new HashSet<>();
        deque.add(vertexFrom);

        while (!deque.isEmpty()) {
            Vertex<T> u = this.extractMin(deque);
            set.add(u);
            for (Edge<T> incEdge : this.getIncidentEdges(u)) {
                for (Vertex<T> vertex : this.getAllVertices()) {
                    if (!set.contains(vertex) && vertex != null) {
                        deque.add(vertex);
                    }
                    if (incEdge.to().equals(vertex)) {
                        this.relax(u, vertex);
                    }
                }
            }
        }
    }

    private Vertex<T> extractMin(Deque<Vertex<T>> deque) {
        Vertex<T> answ = null;
        int distanceAnsw = Integer.MAX_VALUE / 2 + 1;
        for (Vertex<T> vertex : deque) {
            if (this.getDistance(vertex) < distanceAnsw) {
                answ = vertex;
                distanceAnsw = this.getDistance(vertex);
            }
        }
        if (answ != null) {
            deque.remove(answ);
        }
        return answ;
    }
    private void relax(Vertex<T> from, Vertex<T> to) {
        for (Edge<T> edge : this.getIncidentEdges(from)) {
            if (edge.to().equals(to)
                    && (this.getDistance(to) > this.getDistance(from) + edge.weight())) {
                this.setDistance(to, edge.weight() + this.getDistance(from));
            }
        }
    }

    abstract List<Edge<T>> getIncidentEdges(Vertex<T> vertex);

    abstract List<Vertex<T>> getAllVertices();

    abstract void setDistance(Vertex<T> from, int distance);

    abstract int getDistance(Vertex<T> vertex);

    private void initDistance(Vertex<T> from) {
        for (Vertex<T> vertex : this.getAllVertices()) {
            this.setDistance(vertex, Integer.MAX_VALUE / 2);
        }
        this.setDistance(from, 0);
    }

    void show(List<Vertex<T>> sorted) {
        System.out.print("[");
        for (Vertex<T> vertex : sorted) {
            System.out.print(vertex.value()
                    + "(" + this.getDistance(vertex) + "), ");
        }
        System.out.println("]");
    }
    /**
     * Builds graph out of file data.
     *
     * @param fileName name of file with graph data.
     */
    @SuppressWarnings("unchecked")
    public void readFile(String fileName) {
        try (InputStream inpstr = new FileInputStream(fileName)) {
            Scanner scanner = new Scanner(inpstr);

            int vertexCount = 0;
            if (scanner.hasNextInt()) {
                vertexCount = scanner.nextInt();
            }
            for (int i = 0; i < vertexCount; i++) {
                if (scanner.hasNext()) {
                    this.addVertex((T) scanner.next());
                }
            }
            for (int i = 0; i < vertexCount; i++) {
                for (int j = 0; j < vertexCount; j++) {
                    if (scanner.hasNextInt()) {
                        try {
                            this.addEdge(new Edge<>(this.getVertex(i),
                                    this.getVertex(j), scanner.nextInt()));
                        } catch (NullPointerException e) {
                            continue;
                        }
                    } else {
                        if (scanner.hasNext()) {
                            scanner.next();
                        }
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Problems with file");
        }
    }

    /**
    * Sorts and prints vertices in order length of path from source.
    *
    * @param fromValue source of the path.
    */
    public void sortWithPathLengthAndPrint(T fromValue) {
        this.dijkstra(fromValue);

        List<Vertex<T>> sorted = this.sort();

        this.show(sorted);
    }
}
