package com.medg.terraingenerator;

import com.medg.terraingenerator.hexlib.Hex;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RiverTest {

    @Test
    public void testAdd() {
        Hex hex1 = new Hex(0, 0,0);
        Hex hex2 = new Hex(0, 1, -1);
        RiverPair riverPair1 = new RiverPair(hex1, hex2);
        RiverPair riverPair2 = new RiverPair(hex1, hex2);

        River river = new River();
        river.add(riverPair1);
        river.add(riverPair2);
        Set<RiverPair> riverPairs = river.getRiverPairSet();
        assertEquals(1, riverPairs.size());
        assertTrue(riverPairs.contains(riverPair1));
        assertTrue(riverPairs.contains(riverPair2));
    }

}
