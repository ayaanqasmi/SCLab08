package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    // - Test with an empty graph (no vertices or edges)
    // - Test with a graph containing only vertices
    // - Test with a graph containing vertices and edges
    // - Test with multiple edges involving the same vertices

    @Test
    public void testToStringEmptyGraph() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        assertEquals("Vertices: []\nEdges:\n", graph.toString());
    }

    @Test
    public void testToStringGraphWithVerticesOnly() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        String expected = "Vertices: [A, B]\nEdges:\n";
        assertEquals(expected, graph.toString());
    }

    @Test
    public void testToStringGraphWithVerticesAndEdges() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        String expected = "Vertices: [A, B]\nEdges:\nA -> B (weight: 5)\n";
        assertEquals(expected, graph.toString());
    }

    @Test
    public void testToStringGraphWithMultipleEdges() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.set("A", "B", 5);
        graph.set("A", "C", 3);
        String expected = "Vertices: [A, B, C]\nEdges:\nA -> B (weight: 5)\nA -> C (weight: 3)\n";
        assertEquals(expected, graph.toString());
    }

    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    // - Test constructor with valid inputs
    // - Test constructor with invalid inputs (negative weight)
    // - Test getter methods (getSource, getTarget, getWeight)
    // - Test toString() for human-readable representation
    
    @Test
    public void testEdgeConstructorAndGetters() {
        Edge edge = new Edge("A", "B", 5);
        assertEquals("A", edge.getSource());
        assertEquals("B", edge.getTarget());
        assertEquals(5, edge.getWeight());
    }

    @Test(expected = AssertionError.class)
    public void testEdgeNegativeWeight() {

        new Edge("A", "B", -1); // Should fail the checkRep()
    }

    @Test
    public void testEdgeToString() {
        Edge edge = new Edge("A", "B", 10);
        assertEquals("A -> B (weight: 10)", edge.toString());
    }
}
