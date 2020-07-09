package com.medg.terraingenerator.hexlib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LayoutTest {

    @Test
    public void testPixelToHex() {
        Layout layout = new Layout(Orientation.LAYOUT_POINTY, new Point(20, 20), new Point(100, 100));
        Point p1 = new Point(100, 100);
        FractionalHex fractionalHex1 = layout.pixelToHex(p1);
        assertEquals(0.0, fractionalHex1.q, 0.0);
        assertEquals(0.0, fractionalHex1.r, 0.0);
        assertEquals(0.0, fractionalHex1.s, 0.0);

        Point p2 = new Point(140, 100);
        FractionalHex fractionalHex2 = layout.pixelToHex(p2);
        assertEquals(1.15, fractionalHex2.q, 0.01);
        assertEquals(0.0, fractionalHex2.r, 0.01);
        assertEquals(-1.15, fractionalHex2.s,0.01);

    }

}
