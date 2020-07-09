package com.medg.terraingenerator.hexlib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HexMapTest {
    @Test
    public void testMakePointyMap() {
        int height = 2;
        int width = 2;
        Layout layout = new Layout(Orientation.LAYOUT_POINTY, new Point(10,10), new Point(0,0));
        HexMap hexMap = new HexMap(width, height, layout);
        assertEquals(4, hexMap.getHexes().size());
        assertTrue(hexMap.getHexes().contains(new Hex(0,0,0)));
        assertTrue(hexMap.getHexes().contains(new Hex(1, 0, -1)));
        assertTrue(hexMap.getHexes().contains(new Hex(0, 1, -1)));
        assertTrue(hexMap.getHexes().contains(new Hex(1, 1, -2)));
    }

    @Test
    public void testMakeFlatMap() {
        int height = 2;
        int width = 2;
        Layout layout = new Layout(Orientation.LAYOUT_FLAT, new Point(10,10), new Point(0,0));
        HexMap hexMap = new HexMap(width, height, layout);
        assertEquals(4, hexMap.getHexes().size());
        assertTrue(hexMap.getHexes().contains(new Hex(0,0,0)));
        assertTrue(hexMap.getHexes().contains(new Hex(1, 0, -1)));
        assertTrue(hexMap.getHexes().contains(new Hex(0, 1, -1)));
        assertTrue(hexMap.getHexes().contains(new Hex(1, 1, -2)));
    }

}
