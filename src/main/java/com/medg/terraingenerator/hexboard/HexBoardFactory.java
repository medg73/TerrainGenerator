package com.medg.terraingenerator.hexboard;

import com.medg.terraingenerator.dice.Dice;

public class HexBoardFactory {

    private Dice dice;

    public HexBoardFactory(Dice dice) {
        this.dice = dice;
    }

    public HexBoard makeHexBoard(int mapHeight, int mapWidth, int hexSize) {
        return new HexBoard(dice, mapHeight, mapWidth, hexSize);
    }
}
