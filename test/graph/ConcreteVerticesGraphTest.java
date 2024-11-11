/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.Map;
import java.util.Set;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   - Graph with no vertices
    //   - Graph with a single vertex and edge
    //   - Graph with multiple vertices and edges
    //   - Graph with vertices but no edges
    
    @Test
    public void testEmptyGraphToString() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        
        assertEquals("", graph.toString());
    }

    @Test
    public void testGraphToStringWithSingleEdge() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.set("source", "target", 1);
        
        assertEquals("(source -> target, 1)", graph.toString());
    }

    @Test
    public void testGraphToStringWithMultipleEdges() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.set("source", "target", 1);
        graph.set("target", "source", 2);
       
        assertEquals("(source -> target, 1)\n(target -> source, 2)", graph.toString());
    }

    @Test
    public void testGraphToStringWithMultipleVertices() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.set("source", "target", 1);
        graph.set("target", "source", 2);
        graph.set("source", "other", 3);
        System.out.println(graph.toString());
        assertEquals("(source -> other, 3)\n(source -> target, 1)\n(target -> source, 2)", graph.toString());
    }

    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   - Creating a new vertex
    //   - Adding, changing, and removing edges
    //   - String representation of a vertex
    
    @Test
    public void testVertexAddingEdge() {
        Vertex vertex = new Vertex("source");
        int result = vertex.setTarget("target", 1);
        assertEquals("expected edge from source", 0, result);  // 0 because it's the first time we are setting the edge
        assertEquals("expected one target", 1, vertex.getTargets().size());
        assertEquals("expected target with correct weight", Integer.valueOf(1), vertex.getTargets().get("target"));
    }


    @Test
    public void testVertexChangingEdgeWeight() {
        Vertex vertex = new Vertex("source");
        vertex.setTarget("target", 1);
        int result = vertex.setTarget("target", 2);  // Changing the edge weight
        assertEquals("expected previous weight", 1, result);  // Previous weight was 1
        assertEquals("expected new weight", Integer.valueOf(2), vertex.getTargets().get("target"));
    }

    @Test
    public void testVertexRemovingEdge() {
        Vertex vertex = new Vertex("source");
        vertex.setTarget("target", 1);
        int result = vertex.setTarget("target", 0);  // Removing the edge by setting weight to 0
        assertEquals("expected previous weight", 1, result);  // Previous weight was 1
        assertTrue("expected no targets after removal", vertex.getTargets().isEmpty());
    }

    @Test
    public void testVertexRemoveNonExistentEdge() {
        Vertex vertex = new Vertex("source");
        int result = vertex.setTarget("target", 0);  // Trying to remove non-existing edge
        assertEquals("expected no such edge", 0, result);  // No edge existed before
    }

    @Test
    public void testVertexToStringWithSingleEdge() {
        Vertex vertex = new Vertex("source");
        vertex.setTarget("target", 1);
        assertEquals("(source -> target, 1)", vertex.toString());
    }

    @Test
    public void testVertexToStringWithMultipleEdges() {
        Vertex vertex = new Vertex("source");
        vertex.setTarget("target", 1);
        vertex.setTarget("other", 2);
     
        assertEquals("(source -> other, 2)\n(source -> target, 1)", vertex.toString());
    }

    @Test
    public void testVertexToStringWithNoEdges() {
        Vertex vertex = new Vertex("source");
     
        assertEquals("", vertex.toString());  // No edges so only the label appears
    }
   
}
