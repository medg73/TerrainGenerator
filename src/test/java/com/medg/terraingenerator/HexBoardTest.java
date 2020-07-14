package com.medg.terraingenerator;

import com.medg.terraingenerator.dice.Dice;
import com.medg.terraingenerator.dice.RandomNumberGenerator;
import com.medg.terraingenerator.hexlib.Hex;
import com.medg.terraingenerator.hexlib.OffsetCoord;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HexBoardTest {

    @Test
    public void testWeather() {
        Dice dice = mock(Dice.class);
        when(dice.rollPercent()).thenReturn(50).thenReturn(75);
        when(dice.rollD10()).thenReturn(5);
        HexBoard hexBoard = new HexBoard(dice, 2, 2, 40);

        Hex hex1 = new Hex(0,0,0);
        Hex hex2 = new Hex(0, 1, -1);
        Hex hex3 = new Hex(1, 0, -1);
        Hex hex4 = new Hex(1, 1, -2);
        /*
        0,0,0 elevation: 75 row: 0 col: 0
        0,1,-1 elevation: 50 row: 1 col: 0
        1,0,-1 elevation: 75 row: 0 col: 1
        1,1,-2 elevation: 75 row: 1 col: 1
         */

        hexBoard.weather();
        assertEquals(70, hexBoard.getElevation(hex1).intValue());
        assertEquals(50, hexBoard.getElevation(hex2).intValue());
        assertEquals(70, hexBoard.getElevation(hex3).intValue());
        assertEquals(70, hexBoard.getElevation(hex4).intValue());

        River allRivers = hexBoard.getAllRivers();
        assertEquals(3, allRivers.getRiverPairSet().size());
        RiverPair riverPair1 = new RiverPair(hex1, hex2);
        RiverPair riverPair2 = new RiverPair(hex3, hex2);
        RiverPair riverPair3 = new RiverPair(hex4, hex2);

        assertTrue(allRivers.getRiverPairSet().contains(riverPair1));
        assertTrue(allRivers.getRiverPairSet().contains(riverPair2));
        assertTrue(allRivers.getRiverPairSet().contains(riverPair3));

//        for(Hex hex : hexBoard.getHexMap().getHexes()) {
//            OffsetCoord offsetCoord = hexBoard.getHexMap().getOffsetCoord(hex);
//            int row = offsetCoord.row;
//            int col = offsetCoord.col;
//            int elevation = hexBoard.getElevation(hex);
//            System.out.println(hex + " elevation: " + elevation + " row: " + row + " col: " + col);
//        }

    }

}
