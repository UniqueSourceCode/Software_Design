package edu.postech.csed232.exercise3;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;



/**
 * Shared black-box contract tests for Graph implementations.
 *
 * @param <N> type of vertices
 * @param <G> type of Graph
 */
@SuppressWarnings({"null", "nullness"})
@Disabled
abstract public class AbstractGraphTest<N extends Comparable<N>, G extends Graph<N>> {

    G graph;
    N v1, v2, v3, v4, v5, v6, v7, v8;

    @Test
    @DisplayName("addVertex returns true for absent vertex")
    void addVertex_whenAbsent_thenReturnsTrue() {
        assertTrue(graph.addVertex(v1));
    }

    @Test
    @DisplayName("addVertex rejects duplicate vertex")
    void addVertex_whenDuplicate_thenReturnsFalse() {
        assertTrue(graph.addVertex(v6));
        assertTrue(graph.addVertex(v7));
        assertFalse(graph.addVertex(v6));
        assertTrue(graph.containsVertex(v6));
        assertTrue(graph.containsVertex(v7));
    }

    @Test
    @DisplayName("findReachableVertices returns all reachable vertices")
    void findReachableVertices_whenGraphHasMultipleComponents_thenReturnsReachableSet() {
        graph.addEdge(v1, v2);
        graph.addEdge(v1, v4);
        graph.addEdge(v2, v4);
        graph.addEdge(v3, v6);
        graph.addEdge(v6, v3);

        assertEquals(Set.of(v1, v2, v4), graph.findReachableVertices(v1));
        assertEquals(Set.of(v2, v4), graph.findReachableVertices(v2));
        assertEquals(Set.of(v3, v6), graph.findReachableVertices(v3));
        assertEquals(Set.of(v4), graph.findReachableVertices(v4));
        assertTrue(graph.findReachableVertices(v5).isEmpty());
    }

    @Test
    @DisplayName("addEdge returns true for absent edge")
    void addEdge_whenAbsent_thenReturns_true() {
        assertTrue(graph.addEdge(v1, v2));
        assertTrue(graph.addEdge(v1, v4));
        assertTrue(graph.addEdge(v2, v4));
    }

    @Test
    @DisplayName("addEdge returns false for duplicate and self")
    void addEdge_whenDuplicateOrSelf_thenReturns_false() {
        graph.addEdge(v1, v2);
        graph.addEdge(v1, v4);
        graph.addEdge(v2, v4);
        assertFalse(graph.addEdge(v1, v2));
        assertFalse(graph.addEdge(v2, v4));
        assertTrue(graph.addEdge(v4, v1));
        assertFalse(graph.addEdge(v5, v5));
    }

    @Test
    @DisplayName("getNeighborhood returns all sets")
    void getNeighborhood_ReturnsNeighborhoodSet() {
        graph.addEdge(v1, v2);
        graph.addEdge(v1, v4);
        graph.addEdge(v2, v4);
        graph.addEdge(v3, v6);
        graph.addEdge(v6, v3);

        assertEquals(Set.of(v2, v4), graph.getNeighborhood((v1)));
        assertEquals(Set.of(v4), graph.getNeighborhood((v2)));
        assertEquals(Set.of(), graph.getNeighborhood((v4)));
        assertEquals(Set.of(v6), graph.getNeighborhood((v3)));
        assertEquals(Set.of(v3), graph.getNeighborhood((v6)));
        //empty set for non-existing vertex
        assertEquals(Set.of(), graph.getNeighborhood((v5)));
    }

    @Test
    @DisplayName("getVertices returns all vertices")
    void getVertices_ReturnsVertexSet() {
        graph.addEdge(v1, v2);
        graph.addEdge(v1, v4);

        assertEquals(Set.of(v1, v2, v4), graph.getVertices());
    }


    @Test
    @DisplayName("getEdges returns all edges")
    void getEdges_ReturnsEdgeSet() {
        graph.addEdge(v1, v2);
        graph.addEdge(v1, v4);

        assertEquals(Set.of(new Edge<>(v1, v2), new Edge<>(v1, v4)), graph.getEdges());
    }


    @Test
    @DisplayName("removeVertex returns true for existing vertex and false for non-existing vertex, and removes all edges connected to the vertex")
    void removeVertex_whenExisting_thenReturnsTrue() {
        graph.addEdge(v1, v2);
        graph.addEdge(v2, v3);
        assertTrue(graph.removeVertex(v2));
        assertFalse(graph.removeVertex(v2));
        assertFalse(graph.containsVertex(v2));
        assertFalse(graph.containsEdge(v1, v2));
        assertFalse(graph.containsEdge(v2, v3));

    }

    @Test
    @DisplayName("removeEdge returns true for existing edge and false for non-existing edge")
    void removeEdge_whenExisting_thenReturnsTrue() {
        graph.addEdge(v1, v2);
        graph.addEdge(v2, v3);
        assertTrue(graph.removeEdge(v1, v2));
        assertFalse(graph.removeEdge(v1, v2));
        assertTrue(graph.removeEdge(v2, v3));
    }

}
