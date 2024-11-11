package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   Represents a directed, weighted graph where `vertices` is the set of all nodes,
    //   and `edges` contains connections (with weights) between nodes.
    // Representation invariant:
    //   - No two edges in `edges` have the same source and target vertices.
    //   - All vertices in edges must exist in `vertices`.
    // Safety from rep exposure:
    //   - `vertices` and `edges` are private and final, so they cannot be reassigned or exposed directly.
    //   - Methods that return collections make defensive copies to avoid external modifications.

    public ConcreteEdgesGraph() {
        checkRep();
    }

    // checkRep to enforce the representation invariant
    private void checkRep() {
        for (Edge edge : edges) {
            assert vertices.contains(edge.getSource()) && vertices.contains(edge.getTarget());
            for (Edge otherEdge : edges) {
                if (!edge.equals(otherEdge)) {
                    assert !(edge.getSource().equals(otherEdge.getSource()) && edge.getTarget().equals(otherEdge.getTarget()));
                }
            }
        }
    }
    
    @Override
    public boolean add(String vertex) {
        if (vertices.add(vertex)) {
            checkRep();
            return true;
        }
        return false;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        if (weight < 0) throw new IllegalArgumentException("Weight must be non-negative.");
        
        add(source);
        add(target);
        
        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                int oldWeight = edge.getWeight();
                edges.remove(edge);
                if (weight > 0) {
                    edges.add(new Edge(source, target, weight));
                }
                checkRep();
                return oldWeight;
            }
        }
        
        if (weight > 0) {
            edges.add(new Edge(source, target, weight));
        }
        checkRep();
        return 0;
    }
    
    @Override
    public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) return false;
        
        vertices.remove(vertex);
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
        checkRep();
        return true;
    }
    
    @Override
    public Set<String> vertices() {
        return new HashSet<>(vertices);
    }
    
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        return sources;
    }
    
    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targets;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Vertices: ").append(vertices).append("\nEdges:\n");
        for (Edge edge : edges) {
            sb.append(edge.toString()).append("\n");
        }
        return sb.toString();
    }
}

/**
 * Edge represents a directed, weighted edge between two vertices in a graph.
 * This class is immutable.
 */
class Edge {
    
    private final String source;
    private final String target;
    private final int weight;

    // Abstraction function:
    //   Represents a directed edge with a source and target vertex and an integer weight.
    // Representation invariant:
    //   - `weight` must be non-negative.
    // Safety from rep exposure:
    //   - All fields are private, final, and immutable.
    
    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    private void checkRep() {
        assert weight >= 0;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " -> " + target + " (weight: " + weight + ")";
    }
}
