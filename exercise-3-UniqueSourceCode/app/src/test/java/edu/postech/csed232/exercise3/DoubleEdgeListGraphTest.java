package edu.postech.csed232.exercise3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleEdgeListGraphTest extends AbstractGraphTest<Double, EdgeListGraph<Double>> {

    @BeforeEach
    void setUp() {
        graph = new EdgeListGraph<>();
        v1 = 1.1;
        v2 = 2.2;
        v3 = 3.3;
        v4 = 4.4;
        v5 = 5.5;
        v6 = 6.6;
        v7 = 7.7;
        v8 = 8.8;
    }

    @Test
    @DisplayName("Edge's toString returns (source,target)")
    void edgeToString_ReturnsCorrectString() {
        graph.addEdge(v2, v4);
        String edge = graph.getEdges().iterator().next().toString();
        assertEquals(edge, "(2.2,4.4)");
    }

}
