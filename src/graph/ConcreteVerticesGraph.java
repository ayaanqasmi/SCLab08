package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * An implementation of Graph for String labels.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Constructor
    public ConcreteVerticesGraph() {
        checkRep();
    }

    // Check representation invariant
    private void checkRep() {
        Set<String> seenLabels = new HashSet<>();
        for (Vertex vertex : vertices) {
            assert vertex.getLabel() != null;
            assert !seenLabels.contains(vertex.getLabel());
            assert vertex.getTargets().values().stream().allMatch(weight -> weight >= 0);
            seenLabels.add(vertex.getLabel());
        }
    }

    // Adds a new vertex to the graph if it doesn't already exist
    @Override
    public boolean add(String vertexLabel) {
        // Check if vertex already exists
        for (Vertex vertex : vertices) {
            if (vertex.getLabel().equals(vertexLabel)) {
                return false; // Vertex already exists
            }
        }
        // Create and add the new vertex
        vertices.add(new Vertex(vertexLabel));
        checkRep();
        return true;
    }

    // Sets or creates an edge with the given weight between source and target vertices
    @Override
    public int set(String sourceLabel, String targetLabel, int weight) {
        Vertex sourceVertex = findVertex(sourceLabel);
        if (sourceVertex == null) {
            sourceVertex = new Vertex(sourceLabel); // If source doesn't exist, add it
            vertices.add(sourceVertex);
        }

        Vertex targetVertex = findVertex(targetLabel);
        if (targetVertex == null) {
            targetVertex = new Vertex(targetLabel); // If target doesn't exist, add it
            vertices.add(targetVertex);
        }

        // Set the target for the source vertex
        return sourceVertex.setTarget(targetLabel, weight);
    }

    // Removes a vertex and its associated outgoing edges
    @Override
    public boolean remove(String vertexLabel) {
        Vertex vertexToRemove = findVertex(vertexLabel);
        
        if (vertexToRemove == null) {
            return false; // Vertex not found
        }
        
        // Remove all edges to this vertex from other vertices
        for (Vertex vertex : vertices) {
            vertex.removeTarget(vertexLabel);
        }
        
        // Remove the vertex itself
        vertices.remove(vertexToRemove);
        checkRep();
        return true;
    }

    // Returns a set of all vertex labels in the graph
    @Override
    public Set<String> vertices() {
        Set<String> labels = new HashSet<>();
        for (Vertex vertex : vertices) {
            labels.add(vertex.getLabel());
        }
        return labels;
    }

    // Returns a map of all source vertices for a given target vertex
    @Override
    public Map<String, Integer> sources(String targetLabel) {
        Map<String, Integer> sources = new HashMap<>();
        
        for (Vertex vertex : vertices) {
            Map<String, Integer> targets = vertex.getTargets();
            if (targets.containsKey(targetLabel)) {
                sources.put(vertex.getLabel(), targets.get(targetLabel));
            }
        }
        return sources;
    }

    // Returns a map of all target vertices for a given source vertex
    @Override
    public Map<String, Integer> targets(String sourceLabel) {
        Vertex sourceVertex = findVertex(sourceLabel);
        if (sourceVertex == null) {
            return new HashMap<>(); // Source vertex not found
        }
        return sourceVertex.getTargets();
    }

    // Helper method to find a vertex by its label
    private Vertex findVertex(String label) {
        for (Vertex vertex : vertices) {
            if (vertex.getLabel().equals(label)) {
                return vertex;
            }
        }
        return null; // Vertex not found
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Vertex vertex : vertices) {
            result.append(vertex.toString()).append("\n");
        }
        return result.toString().strip();
    }
}

/**
 * Represents a mutable vertex in a graph with a label and outgoing edges with weights.
 * This class is internal to the representation of ConcreteVerticesGraph. 
 */
class Vertex {
    
    private final String label;
    private final Map<String, Integer> targets = new HashMap<>();

    // Constructor
    public Vertex(String label) {
        this.label = label;
        checkRep();
    }

    // Check rep invariant to ensure targets' weights are non-negative
    private void checkRep() {
        assert label != null;
        assert targets.values().stream().allMatch(weight -> weight >= 0);
    }

    /**
     * Returns the label of this vertex.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns a map of target vertices and their associated edge weights.
     */
    public Map<String, Integer> getTargets() {
        return new HashMap<>(targets); // Defensive copy
    }

    /**
     * Sets the edge weight to a target vertex. If weight is 0, removes the edge.
     * 
     * @param target the label of the target vertex
     * @param weight the weight of the edge
     * @return the previous weight of the edge, or 0 if none
     */
    public int setTarget(String target, int weight) {
        int originalWeight = targets.getOrDefault(target, 0);
        if (weight == 0) {
            targets.remove(target); // Remove edge if weight is 0
        } else {
            targets.put(target, weight);
        }
        checkRep();
        return originalWeight;
    }

    /**
     * Removes a target (edge) from this vertex.
     */
    public void removeTarget(String target) {
        targets.remove(target);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        
        for (Map.Entry<String, Integer> entry : targets.entrySet()) {
            result.append("(").append(label)
                  .append(" -> ").append(entry.getKey())
                  .append(", ").append(entry.getValue())
                  .append(")\n");
        }
        return result.toString().strip();
    }
}
