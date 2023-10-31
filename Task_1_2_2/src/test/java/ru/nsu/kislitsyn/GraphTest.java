package ru.nsu.kislitsyn;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    @Test
    void addVerticeInListTest(){
        GraphList<String> graph = new GraphList<>();
        graph.addVertice("Crow");
        assertEquals("Crow", graph.getVertices().get(0).value().value());
    }

    @Test
    void addVerticeInAdjMxTest(){
        GraphAdjMatrix<String> graph = new GraphAdjMatrix<>();
        graph.addVertice("Crow");
        assertEquals("Crow", graph.getVertices().get(0).value());
    }

    @Test
    void addVerticeInIncMxTest(){
        GraphIncMatrix<String> graph = new GraphIncMatrix<>();
        graph.addVertice("Crow");
        assertEquals("Crow", graph.getVertices().get(0).incVertice().value());
    }

    @Test
    void deleteVerticeInListTest(){
        GraphList<String> graph = new GraphList<>();
        graph.addVertice("Crow");
        graph.addVertice("Squirrel");
        assertEquals(2, graph.getVertices().size());
    }

    @Test
    void deleteVerticeInAdjMxTest(){
        GraphAdjMatrix<String> graph = new GraphAdjMatrix<>();
        graph.addVertice("Crow");
        graph.addVertice("Squirrel");
        assertEquals(2, graph.getVertices().size());
    }

    @Test
    void deleteVerticeInIncMxTest(){
        GraphIncMatrix<String> graph = new GraphIncMatrix<>();
        graph.addVertice("Crow");
        graph.addVertice("Squirrel");
        graph.deleteVertice(new Vertice<>("Squirrel"));
        assertEquals(1, graph.getVertices().size());
    }

    @Test
    void fileInput() throws FileNotFoundException {
        GraphList<String> graph = new GraphList<>();

        graph.readFile("src/test/resources/graph.txt");

        graph.sortWithPathLengthAndPrint("C");
    }


}