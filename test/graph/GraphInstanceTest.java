/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   TODO
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // Test add method
    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("Expected vertex to be added successfully", graph.add("A"));
        assertFalse("Expected vertex already exists", graph.add("A"));
        assertTrue(graph.vertices().contains("A"));
    }
    
    // Test set method
    @Test
    public void testSetEdge() {
        Graph<String> graph = emptyInstance();
        
        // Adding edge with new vertices
        assertEquals("Expected no previous edge", 0, graph.set("A", "B", 5));
        
        // Updating existing edge
        assertEquals("Expected previous weight of 5", 5, graph.set("A", "B", 10));
        
        // Removing the edge
        assertEquals("Expected previous weight of 10", 10, graph.set("A", "B", 0));
        assertFalse("Expected edge to be removed", graph.targets("A").containsKey("B"));
    }

    // Test remove method
    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue("Expected vertex to be removed", graph.remove("A"));
        assertFalse("Expected vertex does not exist", graph.remove("A"));
    }
    
    // Additional test for removing a vertex from an empty graph
    @Test
    public void testRemoveVertexFromEmptyGraph() {
        Graph<String> graph = emptyInstance();
        assertFalse("Expected false since vertex does not exist in empty graph", graph.remove("A"));
    }

    // Additional test for removing an edge from an empty graph
    @Test
    public void testRemoveEdgeFromEmptyGraph() {
        Graph<String> graph = emptyInstance();
        assertEquals("Expected 0 since no edge exists in empty graph", 0, graph.set("A", "B", 0));
    }
    
    // Test vertices method
    @Test
    public void testVertices() {
        Graph<String> graph = emptyInstance();
        assertTrue("Expected no vertices in new graph", graph.vertices().isEmpty());
        
        graph.add("A");
        graph.add("B");
        Set<String> vertices = graph.vertices();
        
        assertTrue("Expected vertices to contain A and B", vertices.contains("A") && vertices.contains("B"));
        assertEquals("Expected size of vertices to be 2", 2, vertices.size());
    }

    // Test sources method
    @Test
    public void testSources() {
        Graph<String> graph = emptyInstance();
        graph.set("A", "B", 3);
        graph.set("C", "B", 4);
        
        Map<String, Integer> sources = graph.sources("B");
        assertEquals("Expected source A with weight 3", (Integer) 3, sources.get("A"));
        assertEquals("Expected source C with weight 4", (Integer) 4, sources.get("C"));
        assertEquals("Expected 2 sources", 2, sources.size());
    }

    // Test targets method
    @Test
    public void testTargets() {
        Graph<String> graph = emptyInstance();
        graph.set("A", "B", 3);
        graph.set("A", "C", 4);
        
        Map<String, Integer> targets = graph.targets("A");
        assertEquals("Expected target B with weight 3", (Integer) 3, targets.get("B"));
        assertEquals("Expected target C with weight 4", (Integer) 4, targets.get("C"));
        assertEquals("Expected 2 targets", 2, targets.size());
    }

    // Additional test for sources on an empty graph
    @Test
    public void testSourcesEmptyGraph() {
        Graph<String> graph = emptyInstance();
        assertTrue("Expected sources to be empty for non-existent vertex", graph.sources("A").isEmpty());
    }

    // Additional test for targets on an empty graph
    @Test
    public void testTargetsEmptyGraph() {
        Graph<String> graph = emptyInstance();
        assertTrue("Expected targets to be empty for non-existent vertex", graph.targets("A").isEmpty());
    }
    
}
