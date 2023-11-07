package ru.nsu.kislitsyn;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class GraphTest {
    @Test
    void addVerticeInListTest() {
        GraphList<String> graph = new GraphList<>();
        graph.addVertex("Crow");
        assertEquals("Crow", graph.getVertices().get(0).getValue().value());
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
        assertEquals(2, graph.getFullVertex(1).getDistance());
        assertEquals(10, graph.getFullVertex(5).getDistance());

    }

    @Test
    void vertexSetInListTest() {
        GraphList<Integer> graph = new GraphList<>();

        graph.addVertex(2);
        assertEquals(2, graph.getVertex(0).value());
        graph.setVertex(new Vertex<>(2), 4);
        assertEquals(4, graph.getVertex(0).value());
    }

    @Test
    void vertexSetInAdjMxTest() {
        GraphAdjMatrix<Integer> graph = new GraphAdjMatrix<>();

        graph.addVertex(2);
        assertEquals(2, graph.getVertex(0).value());
        graph.setVertex(new Vertex<>(2), 4);
        assertEquals(4, graph.getVertex(0).value());
    }
    @Test
    void vertexSetInIncMxTest() {
        GraphIncMatrix<Integer> graph = new GraphIncMatrix<>();

        graph.addVertex(2);
        assertEquals(2, graph.getVertex(0).value());
        graph.setVertex(new Vertex<>(2), 4);
        assertEquals(4, graph.getVertex(0).value());
    }



}