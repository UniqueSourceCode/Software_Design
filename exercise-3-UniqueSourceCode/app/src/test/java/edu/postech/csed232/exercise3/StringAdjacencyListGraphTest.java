package edu.postech.csed232.exercise3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringAdjacencyListGraphTest extends AbstractGraphTest<String, AdjacencyListGraph<String>> {

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph<>();
        v1 = "1";
        v2 = "2";
        v3 = "3";
        v4 = "4";
        v5 = "5";
        v6 = "6";
        v7 = "7";
        v8 = "8";
    }

    @Test
    @DisplayName("Edge's toString returns (source,target)")
    void edgeToString_ReturnsCorrectString() {
        graph.addEdge(v3, v5);
        String edge = graph.getEdges().iterator().next().toString();
        assertEquals(edge, "(3,5)");
    }

    @Test
    @DisplayName("AdjacencyListGraph's toString returns (source,target)")
    void graphToString_ReturnsCorrectString() {
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addEdge(v2,v4);
        graph.addEdge(v5,v6);
        String graphString = graph.toString();
        assertEquals(graphString, "[vertex: {1, 2, 3, 4, 5, 6}, edge: {(2,4), (5,6)}]");
    }

    
}
