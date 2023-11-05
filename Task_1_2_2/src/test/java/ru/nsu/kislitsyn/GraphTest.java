package ru.nsu.kislitsyn;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class GraphTest {
    @Test
    void addVerticeInListTest() {
        GraphList<String> graph = new GraphList<>();
        graph.addVertex("Crow");
        assertEquals("Crow", graph.getVertices().get(0).value().value());
    }

    @Test
    void addVerticeInAdjMxTest() {
        GraphAdjMatrix<String> graph = new GraphAdjMatrix<>();
        graph.addVertex("Crow");
        assertEquals("Crow", graph.getVertices().get(0).getValue().value());
    }

    @Test
    void addVerticeInIncMxTest() {
        GraphIncMatrix<String> graph = new GraphIncMatrix<>();
        graph.addVertex("Crow");
        assertEquals("Crow", graph.getMatrix().getLine(0).get(0).getVertex().value());
    }

    @Test
    void deleteVerticeInListTest() {
        GraphList<String> graph = new GraphList<>();
        graph.addVertex("Crow");
        graph.addVertex("Squirrel");
        assertEquals(2, graph.getVertices().size());
    }

    @Test
    void deleteVerticeInAdjMxTest() {
        GraphAdjMatrix<String> graph = new GraphAdjMatrix<>();
        graph.addVertex("Crow");
        graph.addVertex("Squirrel");
        assertEquals(2, graph.getVertices().size());
    }

    @Test
    void deleteVerticeInIncMxTest() {
        GraphIncMatrix<String> graph = new GraphIncMatrix<>();
        graph.addVertex("Crow");
        graph.addVertex("Squirrel");
        graph.deleteVertex(new Vertex<>("Squirrel"));
        assertEquals(1, graph.getMatrix().getMatrix().size());
    }

    @Test
    void fileInput() throws IOException {
        GraphList<String> graph = new GraphList<>();

        graph.readFile("src/test/resources/graph.txt");

        graph.sortWithPathLengthAndPrint("C");
    }


}