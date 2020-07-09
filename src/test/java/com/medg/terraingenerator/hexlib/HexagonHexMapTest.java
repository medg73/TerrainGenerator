package com.medg.terraingenerator.hexlib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HexagonHexMapTest {

    @Test
    public void testMakeHexagonHexMap() {
        int radius = 1;
        HexagonHexMap hexMap = new HexagonHexMap(radius);
        assertEquals(7, hexMap.getHexes().size());
        for(Hex hex : hexMap.getHexes()) {
            assertTrue(hex.getQ() + hex.getR() + hex.getS() == 0);
            assertTrue(hex.getQ() <= 1 && hex.getQ() >= -1);
            assertTrue(hex.getR() <= 1 && hex.getR() >= -1);
            assertTrue(hex.getS() <= 1 && hex.getS() >= -1);
        }

    }
}
