package com.medg.terraingenerator.hexlib;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class DirectedEdgeTest {

    @Test
    public void testConstructor() {
        Hex hex1 = new Hex(0,0,0);
        Hex hex2 = new Hex(10, 0, -10);
        try {
            DirectedEdge directedEdge = new DirectedEdge(hex1, hex2);
            fail();
        } catch(AssertionError assertionError) {
            //
        }
    }

    @Test
    public void testEquals() {
        Hex hex1 = new Hex(0, 0, 0);
        Hex hex2 = new Hex(0, 1, -1);
        Hex hex3 = new Hex(1, 0, -1);

        DirectedEdge directedEdge1 = new DirectedEdge(hex1, hex2);
        DirectedEdge directedEdge2 = new DirectedEdge(hex1, hex2);
        DirectedEdge directedEdge3 = new DirectedEdge(hex2, hex1);
        DirectedEdge directedEdge4 = new DirectedEdge(hex1, hex3);
        assertEquals(directedEdge1, directedEdge2);
        assertNotEquals(directedEdge1, directedEdge3);
        assertNotEquals(directedEdge1, directedEdge4);
    }

    @Test
    public void testHashCode() {
        Hex hex1 = new Hex(0, 0, 0);
        Hex hex2 = new Hex(0, 1, -1);

        DirectedEdge directedEdge1 = new DirectedEdge(hex1, hex2);
        DirectedEdge directedEdge2 = new DirectedEdge(hex1, hex2);
        DirectedEdge directedEdge3 = new DirectedEdge(hex2, hex1);

        assertEquals(directedEdge1.hashCode(), directedEdge2.hashCode());
        assertNotEquals(directedEdge1.hashCode(), directedEdge3.hashCode());
    }
}
