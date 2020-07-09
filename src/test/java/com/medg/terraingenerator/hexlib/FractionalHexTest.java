package com.medg.terraingenerator.hexlib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FractionalHexTest {

    @Test
    public void testRound() {
        FractionalHex fractionalHex1 = new FractionalHex(0.0, 0.0, 0.0);
        assertEquals(new Hex(0, 0, 0), fractionalHex1.round());

        FractionalHex fractionalHex2 = new FractionalHex(1.01, 0.2, -0.95);
        assertEquals(new Hex(1, 0, -1), fractionalHex2.round());

        FractionalHex fractionalHex3 = new FractionalHex(-1.2, 1.01, 0.1);
        assertEquals(new Hex(-1, 1, 0), fractionalHex3.round());
    }
}
