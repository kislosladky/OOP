package ru.nsu.kislitsyn;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

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

    /**
     * Builds graph out of file data.
     *
     * @param fileName name of file with graph data.
     */
    @SuppressWarnings("unchecked")
    public void readFile(String fileName) {
        try (InputStream inpstr = new FileInputStream(fileName)){
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
                        this.addEdge(new Edge<>(this.getVertex(i),
                                this.getVertex(j), scanner.nextInt()));
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
}
