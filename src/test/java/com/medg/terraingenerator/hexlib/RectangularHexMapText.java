package com.medg.terraingenerator.hexlib;

import org.junit.Test;

import java.util.IllegalFormatWidthException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RectangularHexMapText {
    @Test
    public void testMakePointyMap() {
        int height = 2;
        int width = 2;
        Layout layout = new Layout(Orientation.LAYOUT_POINTY, new Point(10,10), new Point(0,0));
        RectangularHexMap hexMap = new RectangularHexMap(width, height, layout);
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
        RectangularHexMap hexMap = new RectangularHexMap(width, height, layout);
        assertEquals(4, hexMap.getHexes().size());
        assertTrue(hexMap.getHexes().contains(new Hex(0,0,0)));
        assertTrue(hexMap.getHexes().contains(new Hex(1, 0, -1)));
        assertTrue(hexMap.getHexes().contains(new Hex(0, 1, -1)));
        assertTrue(hexMap.getHexes().contains(new Hex(1, 1, -2)));
    }

    @Test
    public void testGetOffsetCoordPointy() {
        int height = 2;
        int width = 2;
        Layout layout = new Layout(Orientation.LAYOUT_POINTY, new Point(10,10), new Point(0,0));
        RectangularHexMap hexMap = new RectangularHexMap(width, height, layout);

        Hex hex1 = new Hex(0, 0, 0);
        Hex hex2 = new Hex(1, 1, -2);
        Hex hex3 = new Hex(1, 0, -1);
        Hex hex4 = new Hex(3, 0, -3);

        OffsetCoord offsetCoord1 = hexMap.getOffsetCoord(hex1);
        OffsetCoord offsetCoord2 = hexMap.getOffsetCoord(hex2);
        OffsetCoord offsetCoord3 = hexMap.getOffsetCoord(hex3);
        OffsetCoord offsetCoord4 = hexMap.getOffsetCoord(hex4);

        assertEquals(offsetCoord1.toString(), new OffsetCoord(0, 0), offsetCoord1);
        assertEquals(offsetCoord2.toString(), new OffsetCoord(1, 1), offsetCoord2);
        assertEquals(offsetCoord3.toString(), new OffsetCoord(0, 1), offsetCoord3);
        assertEquals(offsetCoord4.toString(), new OffsetCoord(0, 3), offsetCoord4);

        assertEquals(hex1, hexMap.getHex(offsetCoord1));
        assertEquals(hex2, hexMap.getHex(offsetCoord2));
        assertEquals(hex3, hexMap.getHex(offsetCoord3));
        assertEquals(hex4, hexMap.getHex(offsetCoord4));

    }

    @Test
    public void testGetOffsetCoordFlat() {
        int height = 2;
        int width = 2;
        Layout layout = new Layout(Orientation.LAYOUT_FLAT, new Point(10,10), new Point(0,0));
        RectangularHexMap hexMap = new RectangularHexMap(width, height, layout);

        Hex hex1 = new Hex(0, 0, 0);
        Hex hex2 = new Hex(1, 1, -2);
        Hex hex3 = new Hex(1, 0, -1);
        Hex hex4 = new Hex(3, 0, -3);

        OffsetCoord offsetCoord1 = hexMap.getOffsetCoord(hex1);
        OffsetCoord offsetCoord2 = hexMap.getOffsetCoord(hex2);
        OffsetCoord offsetCoord3 = hexMap.getOffsetCoord(hex3);
        OffsetCoord offsetCoord4 = hexMap.getOffsetCoord(hex4);

        assertEquals(offsetCoord1.toString(), new OffsetCoord(0, 0), offsetCoord1);
        assertEquals(offsetCoord2.toString(), new OffsetCoord(1, 1), offsetCoord2);
        assertEquals(offsetCoord3.toString(), new OffsetCoord(0, 1), offsetCoord3);
        assertEquals(offsetCoord4.toString(), new OffsetCoord(1, 3), offsetCoord4);

        assertEquals(hex1, hexMap.getHex(offsetCoord1));
        assertEquals(hex2, hexMap.getHex(offsetCoord2));
        assertEquals(hex3, hexMap.getHex(offsetCoord3));
        assertEquals(hex4, hexMap.getHex(offsetCoord4));
    }

}
