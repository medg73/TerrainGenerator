package com.medg.terraingenerator;

import com.medg.terraingenerator.hexlib.Hex;
import org.junit.Test;

import static org.junit.Assert.*;

public class RiverPairTest {

    @Test
    public void testConstructor() {
        Hex hex1 = new Hex(0,0,0);
        Hex hex2 = new Hex(10, 0, -10);
        try {
            RiverPair riverPair = new RiverPair(hex1, hex2);
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

        RiverPair riverPair1 = new RiverPair(hex1, hex2);
        RiverPair riverPair2 = new RiverPair(hex1, hex2);
        RiverPair riverPair3 = new RiverPair(hex2, hex1);
        RiverPair riverPair4 = new RiverPair(hex1, hex3);
        assertEquals(riverPair1, riverPair2);
        assertEquals(riverPair1, riverPair3);
        assertNotEquals(riverPair1, riverPair4);
    }

    @Test
    public void testHashCode() {
        Hex hex1 = new Hex(0, 0, 0);
        Hex hex2 = new Hex(0, 1, -1);


        RiverPair riverPair1 = new RiverPair(hex1, hex2);
        RiverPair riverPair2 = new RiverPair(hex1, hex2);
        RiverPair riverPair3 = new RiverPair(hex2, hex1);

        assertEquals(riverPair1.hashCode(), riverPair2.hashCode());
        assertEquals(riverPair1.hashCode(), riverPair3.hashCode());
    }

}
