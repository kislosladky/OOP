package ru.nsu.kislitsyn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    @Test
    void addNodeTest(){
        GraphList<String> graph = new GraphList<>();
        graph.addVertice("Crow");
        assertEquals("Crow", graph.getVertices().get(0).getValue());
    }
}