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
        graph.deleteVertex(new Vertex<>("Squirrel"));
        assertEquals(1, graph.getVertices().size());
    }

    @Test
    void deleteVerticeInAdjMxTest() {
        GraphAdjMatrix<String> graph = new GraphAdjMatrix<>();
        graph.addVertex("Crow");
        graph.addVertex("Squirrel");
        graph.deleteVertex(new Vertex<>("Squirrel"));
        assertEquals(1, graph.getVertices().size());
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

    @Test
    void matrixTest() {
        Matrix<Integer, Integer> matrix = new Matrix<>();
        matrix.addLine(0);
        matrix.addLine(1);
        matrix.addColumn(0);
        matrix.addColumn(1);
        assertEquals(0, matrix.getLineValue(0).getValue());
        assertEquals(1, matrix.getColumnByIndex(0,1));
    }

    @Test
    void matrixDeleteTest() {
        Matrix<Integer, Integer> matrix = new Matrix<>();
        matrix.addLine(0);
        matrix.addLine(1);
        matrix.addLine(4);
        matrix.addLine(-2);
        matrix.addColumn(0);
        matrix.addColumn(1);

        matrix.removeColumnByIndex(0);
        matrix.removeLineByValue(4);
        assertEquals(3, matrix.getMatrix().size());
        assertEquals(1, matrix.getLineValue(0).getColumns().size());
        assertEquals(-2, matrix.getLineValue(2).getValue());
    }


    @Test
    void adjMxAddEdgeTest() {
        GraphAdjMatrix<String> graph = new GraphAdjMatrix<>();
        graph.addVertex("Crow");
        graph.addVertex("Raven");
        graph.addEdge(new Edge<>(new Vertex<>("Crow"), new Vertex<>("Raven"), 4));

        assertEquals(4, graph.getMatrix().getLine(0).get(1).getWeight());
    }

    @Test
    void adjMxDeleteEdgeTest() {
        GraphAdjMatrix<String> graph = new GraphAdjMatrix<>();
        graph.addVertex("Crow");
        graph.addVertex("Raven");
        var edge = graph.addEdge(new Edge<>(new Vertex<>("Crow"), new Vertex<>("Raven"), 4));
        graph.deleteEdge(edge);
        assertEquals(-1, graph.getMatrix().getLine(0).get(1).getWeight());
    }

    @Test
    void adjMxSetEdgeTest() {
        GraphAdjMatrix<String> graph = new GraphAdjMatrix<>();
        graph.addVertex("Crow");
        graph.addVertex("Raven");
        var edge = graph.addEdge(new Edge<>(new Vertex<>("Crow"), new Vertex<>("Raven"), 4));
        graph.setEdge(edge, 82);
        assertEquals(82, graph.getMatrix().getLine(0).get(1).getWeight());
    }

    @Test
    void adjMxSetVertexTest() {
        GraphAdjMatrix<String> graph = new GraphAdjMatrix<>();
        graph.addVertex("Crow");
        graph.addVertex("Raven");
        graph.setVertex(new Vertex<>("Crow"), "Seagull");
        assertEquals("Seagull", graph.getVertex(0).value());
    }
    @Test
    void adjIncSetVertexTest() {
        GraphIncMatrix<String> graph = new GraphIncMatrix<>();
        graph.addVertex("Crow");
        graph.addVertex("Raven");
        graph.setVertex(new Vertex<>("Crow"), "Seagull");
        assertEquals("Seagull", graph.getVertex(0).value());
    }
}