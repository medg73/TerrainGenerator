package com.medg.terraingenerator.hexlib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HexTest {

    @Test
    public void testHexAdd() {
        Hex hex1 = new Hex(0,0,0);
        Hex hex2 = new Hex(2, -2, 0);
        Hex expected = new Hex(2, -2, 0);
        assertEquals(expected, hex1.add(hex2));
    }

    @Test
    public void testHexSubtract() {
        Hex hex1 = new Hex(0,0,0);
        Hex hex2 = new Hex(2, -2, 0);
        Hex expected1 = new Hex(-2, 2, 0);
        Hex expected2 = hex2;
        assertEquals(expected1, hex1.subtract(hex2));
        assertEquals(expected2, hex2.subtract(hex1));
    }

    @Test
    public void testHexMultiply() {
        Hex hex1 = new Hex(3,-1,-2);
        int k = 4;
        Hex expected1 = new Hex(12, -4, -8);
        assertEquals(expected1, hex1.multiply(k));
    }

    @Test
    public void testHexDistance() {
        Hex hex1 = new Hex(-1, -1, 2);
        Hex hex2 = new Hex(-3, 2, 1);
        assertEquals(3, hex1.distance(hex2));
        assertEquals(3, hex2.distance(hex1));
    }

    @Test
    public void testHexNeighbor() {
        Hex hexBase = new Hex(-1, -1, 2);
        Hex hex0 = new Hex(0, -1, 1);
        Hex hex1 = new Hex(0, -2, 2);
        Hex hex2 = new Hex(-1, -2, 3);
        Hex hex3 = new Hex(-2, -1, 3);
        Hex hex4 = new Hex(-2, 0, 2);
        Hex hex5 = new Hex(-1, 0, 1);

        assertEquals(hex0, hexBase.neighbor(0));
        assertEquals(hex1, hexBase.neighbor(1));
        assertEquals(hex2, hexBase.neighbor(2));
        assertEquals(hex3, hexBase.neighbor(3));
        assertEquals(hex4, hexBase.neighbor(4));
        assertEquals(hex5, hexBase.neighbor(5));
    }

    @Test
    public void testToPixel() {
        Point size = new Point(10,10);
        Point origin = new Point(0, 0);
        Layout layout = new Layout(Orientation.LAYOUT_POINTY, size, origin);

        Hex hex1 = new Hex(0, 0, 0);
        Point pixel1 = hex1.toPixel(layout);
        assertEquals(0.0, pixel1.x, 0.0);
        assertEquals(0.0, pixel1.y, 0.0);

        Hex hex2 = new Hex(1, 0, -1);
        Point pixel2 = hex2.toPixel(layout);
        // (10 * cos(30deg)) * 2 = distance between center points
        assertEquals(17.32050807568877, pixel2.x, 0.0);
        assertEquals(0.0, pixel2.y, 0.0);

        Hex hex3 = new Hex(2, 0, -2);
        Point pixel3 = hex3.toPixel(layout);
        assertEquals(34.64101615137754, pixel3.x, 0.0);
        assertEquals(0.0, pixel3.y, 0.0);

        Hex hex4 = new Hex(-1, 2, -1);
        Point pixel4 = hex4.toPixel(layout);
        assertEquals(0.0, pixel4.x, 0.0);
        assertEquals(30.0, pixel4.y, 0.0);

    }

    @Test
    public void testPolygonCorners() {
        Point size = new Point(10,10);
        Point origin = new Point(0, 0);
        Layout layout = new Layout(Orientation.LAYOUT_POINTY, size, origin);
        Hex hex1 = new Hex(0, 0, 0);
        Point[] corners = hex1.polygonCorners(layout);

        assertEquals(8.66, corners[0].x, 0.001);
        assertEquals(5.0, corners[0].y, 0.001);
        assertEquals(0.0, corners[1].x, 0.001);
        assertEquals(10.0, corners[1].y, 0.001);
        assertEquals(-8.66, corners[2].x, 0.001);
        assertEquals(5.0, corners[2].y, 0.001);
        assertEquals(-8.66, corners[3].x, 0.001);
        assertEquals(-5.0, corners[3].y, 0.001);
        assertEquals(0.0, corners[4].x, 0.001);
        assertEquals(-10.0, corners[4].y, 0.001);
        assertEquals(8.66, corners[5].x, 0.001);
        assertEquals(-5.0, corners[5].y, 0.001);

    }

    @Test
    public void testLinedraw() {
        Hex start = new Hex(0,0,0);
        Hex end = new Hex(3, -1, -2);
        Hex[] lineHexes = start.linedraw(end);
        assertEquals(4, lineHexes.length);
        assertEquals(start, lineHexes[0]);
        assertEquals(new Hex(1, 0, -1), lineHexes[1]);
        assertEquals(new Hex(2, -1, -1), lineHexes[2]);
        assertEquals(end, lineHexes[3]);

        Hex[] lineHexes2 = start.linedraw(start);
        assertEquals(1, lineHexes2.length);
        assertEquals(start, lineHexes[0]);

        Hex[] lineHexes3 = end.linedraw(start);
        assertEquals(4, lineHexes3.length);
        assertEquals(end, lineHexes3[0]);
        assertEquals(new Hex(2, -1, -1), lineHexes3[1]);
        assertEquals(new Hex(1, 0, -1), lineHexes3[2]);
        assertEquals(start, lineHexes3[3]);
    }
}
