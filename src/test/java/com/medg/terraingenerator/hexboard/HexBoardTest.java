package com.medg.terraingenerator.hexboard;

import com.medg.terraingenerator.dice.Dice;
import com.medg.terraingenerator.hexboard.HexBoard;
import com.medg.terraingenerator.hexlib.DirectedEdge;
import com.medg.terraingenerator.hexlib.Hex;
import com.medg.terraingenerator.hexlib.OffsetCoord;
import com.medg.terraingenerator.hexlib.Orientation;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HexBoardTest {

    @Test
    public void testWeather() {
        Dice dice = mock(Dice.class);
//        when(dice.rollPercent()).thenReturn(50).thenReturn(75);
        when(dice.rollD10()).thenReturn(5);
        HexBoard hexBoard = new HexBoard(dice, 2, 2, 40, Orientation.LAYOUT_POINTY);

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

        hexBoard.setHexElevation(hex1, 75);
        hexBoard.setHexElevation(hex2, 50);
        hexBoard.setHexElevation(hex3, 75);
        hexBoard.setHexElevation(hex4, 85);
        hexBoard.weather();
        assertEquals(70, hexBoard.getElevation(hex1).intValue());
        assertEquals(50, hexBoard.getElevation(hex2).intValue());
        assertEquals(70, hexBoard.getElevation(hex3).intValue());
        assertEquals(80, hexBoard.getElevation(hex4).intValue());

        Set<DirectedEdge> allRivers = hexBoard.getAllRiverEdges();
        assertEquals(3, allRivers.size());
        DirectedEdge riverPair1 = new DirectedEdge(hex1, hex2);
        DirectedEdge riverPair2 = new DirectedEdge(hex3, hex2);
        DirectedEdge riverPair3 = new DirectedEdge(hex4, hex2);

        assertTrue(allRivers.contains(riverPair1));
        assertTrue(allRivers.contains(riverPair2));
        assertTrue(allRivers.contains(riverPair3));

        int flow1 = hexBoard.getFlowIntoHex(hex1);
        int flow2 = hexBoard.getFlowIntoHex(hex2);
        int flow3 = hexBoard.getFlowIntoHex(hex3);
        int flow4 = hexBoard.getFlowIntoHex(hex4);

        assertEquals(15, flow2);
        assertEquals(0, flow1);
        assertEquals(0, flow3);
        assertEquals(0, flow4);

//        for(Hex hex : hexBoard.getHexMap().getHexes()) {
//            OffsetCoord offsetCoord = hexBoard.getHexMap().getOffsetCoord(hex);
//            int row = offsetCoord.row;
//            int col = offsetCoord.col;
//            int elevation = hexBoard.getElevation(hex);
//            System.out.println(hex + " elevation: " + elevation + " row: " + row + " col: " + col);
//        }

    }

    @Test
    public void testWaterFlow() {
        Dice dice = mock(Dice.class);
        when(dice.rollD10()).thenReturn(5);
        HexBoard hexBoard = new HexBoard(dice, 1, 4, 40, Orientation.LAYOUT_POINTY);
        Hex hex1 = hexBoard.getHexByOffsetCoord(new OffsetCoord(0, 0));
        Hex hex2 = hexBoard.getHexByOffsetCoord(new OffsetCoord(0, 1));
        Hex hex3 = hexBoard.getHexByOffsetCoord(new OffsetCoord(0, 2));
        Hex hex4 = hexBoard.getHexByOffsetCoord(new OffsetCoord(0, 3));

        hexBoard.setHexElevation(hex1, 0);
        hexBoard.setHexElevation(hex2, 20);
        hexBoard.setHexElevation(hex3, 30);
        hexBoard.setHexElevation(hex4, 40);

        hexBoard.weather();

        assertEquals(15, hexBoard.getFlowIntoHex(hex1).intValue());
        assertEquals(10, hexBoard.getFlowIntoHex(hex2).intValue());
        assertEquals(5, hexBoard.getFlowIntoHex(hex3).intValue());
        assertEquals(0, hexBoard.getFlowIntoHex(hex4).intValue());

        DirectedEdge riverPair1 = new DirectedEdge(hex4, hex3);
        DirectedEdge riverPair2 = new DirectedEdge(hex3, hex2);
        DirectedEdge riverPair3 = new DirectedEdge(hex2, hex1);
        assertEquals(5, hexBoard.getRiverFlowByEdge(riverPair1));
        assertEquals(10, hexBoard.getRiverFlowByEdge(riverPair2));
        assertEquals(15, hexBoard.getRiverFlowByEdge(riverPair3));

        DirectedEdge badRiverPair = new DirectedEdge(hex1,hex2);
        assertEquals(0, hexBoard.getRiverFlowByEdge(badRiverPair));

    }

}
