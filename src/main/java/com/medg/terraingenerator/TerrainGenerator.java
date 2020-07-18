package com.medg.terraingenerator;

import com.medg.terraingenerator.dice.Dice;
import com.medg.terraingenerator.dice.RandomNumberGenerator;
import com.medg.terraingenerator.hexboard.HexBoardFactory;
import com.medg.terraingenerator.ui.TerrainGeneratorFrame;

import javax.swing.*;

public class TerrainGenerator {

    private static Dice dice;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());

        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
        dice = new Dice(randomNumberGenerator);
        HexBoardFactory hexBoardFactory = new HexBoardFactory(dice);
        TerrainGeneratorFrame terrainGeneratorFrame = new TerrainGeneratorFrame(hexBoardFactory, dice);
    }

}