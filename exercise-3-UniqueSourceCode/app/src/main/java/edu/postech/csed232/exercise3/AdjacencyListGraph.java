package edu.postech.csed232.exercise3;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
/**
 * An implementation of Graph with an adjacency list representation.
 * NOTE: you should NOT add more member variables to this class.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class AdjacencyListGraph<N extends Comparable<N>> implements Graph<N> {

    /**
     * A map from vertices to the sets of their adjacent vertices. E.g., a graph
     * with vertices {v1, v2, v3, v4} and edges {(v1,v2), (v1,v4), (v2, v4)} is
     * represented by the map {v1 |-> {v2, v4}, v2 |-> {v4}, v3 |-> {}, v4 |-> {}}.
     */
    private final @NotNull SortedMap<N, SortedSet<N>> adjMap;

    /**
     * Creates an empty graph
     */
    public AdjacencyListGraph() {
        adjMap = new TreeMap<>();
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        return adjMap.containsKey(vertex);
    }

    @Override
    public boolean addVertex(@NotNull N vertex) {
        if (containsVertex(vertex)) {
            return false;
        }
        adjMap.put(vertex, new TreeSet<>());
        return true;
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        if (!containsVertex(vertex)) {
            return false;
        }
        adjMap.remove(vertex);
        for (var neighbors : adjMap.values()) {
            neighbors.remove(vertex);
        }
        return true;
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        if (!containsVertex(source) || !containsVertex(target)) {
            return false;
        }
        return adjMap.get(source).contains(target);
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        if (source == null || target == null || source.equals(target)) {
            return false;
        }
        
        if (containsEdge(source, target)) {
            return false;
        }
        
        addVertex(source);
        addVertex(target);
        adjMap.get(source).add(target);
        return true;
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        if (!containsEdge(source, target)) {
            return false;
        }
        adjMap.get(source).remove(target);
        return true;
    }

    @Override
    public @NotNull Set<N> getNeighborhood(N vertex) {
        if (!containsVertex(vertex)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(adjMap.get(vertex));
    }

    @Override
    public @NotNull Set<N> getVertices() {
        return Collections.unmodifiableSet(adjMap.keySet());
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        return adjMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(n -> new Edge<>(entry.getKey(), n)))
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Provides a human-readable string representation for the abstract value of the
     * graph
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        return String.format("[vertex: {%s}, edge: {%s}]",
                getVertices().stream().map(N::toString).collect(Collectors.joining(", ")),
                getEdges().stream().map(Edge::toString).collect(Collectors.joining(", ")));
    }
}
