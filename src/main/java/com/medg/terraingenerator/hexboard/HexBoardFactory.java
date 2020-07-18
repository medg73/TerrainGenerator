package com.medg.terraingenerator.hexboard;

import com.medg.terraingenerator.dice.Dice;
import com.medg.terraingenerator.hexlib.Orientation;

public class HexBoardFactory {

    public static final Orientation DefaultOrientation = Orientation.LAYOUT_POINTY;
    private Dice dice;


    public HexBoardFactory(Dice dice) {
        this.dice = dice;
    }

    public HexBoard makeHexBoard(int mapHeight, int mapWidth, int hexSize, Orientation orientation) {
        return new HexBoard(dice, mapHeight, mapWidth, hexSize, orientation);
    }
}
