package edu.postech.csed232.exercise3;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * An implementation of Graph with an edge list representation.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class EdgeListGraph<N extends Comparable<N>> implements Graph<N> {

    /**
     * A set of vertices in the graph
     */
    private final @NotNull Set<N> vertices;

    /**
     * A list of edges in the graph
     */
    private final @NotNull List<Edge<N>> edges;

    /**
     * Creates an empty graph
     */
    public EdgeListGraph() {
        vertices = new HashSet<>();
        edges = new ArrayList<>();
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        return vertices.contains(vertex);
    }

    @Override
    public boolean addVertex(@NotNull N vertex) {
        if (containsVertex(vertex)) {
            return false;
        }
        vertices.add(vertex);
        return true;
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        if (!containsVertex(vertex)) {
            return false;
        }
        vertices.remove(vertex);
        edges.removeIf(edge -> edge.source().equals(vertex) || edge.target().equals(vertex));
        return true;
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        return edges.contains(new Edge<>(source, target));
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        if (source.equals(target)) {
            return false;
        }
        if (containsEdge(source, target)) {
            return false;
        }

        addVertex(source);
        addVertex(target);
        edges.add(new Edge<>(source, target));
        return true;
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        if (!containsEdge(source, target)) {
            return false;
        }
        edges.remove(new Edge<>(source, target));
        return true;
    }

    @Override
    public @NotNull Set<N> getNeighborhood(N vertex) {
        Set<N> neighborhood = new HashSet<>();
        for (var edge : edges) {
            if (edge.source().equals(vertex)) {
                neighborhood.add(edge.target());
            }
        }
        return Collections.unmodifiableSet(neighborhood);
    }

    @Override
    public @NotNull Set<N> getVertices() {
        return Collections.unmodifiableSet(vertices);
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        return Set.copyOf(edges);
    }
}
