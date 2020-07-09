package com.medg.terraingenerator;

import com.medg.terraingenerator.dice.Dice;
import com.medg.terraingenerator.dice.RandomNumberGenerator;

import javax.swing.*;

public class TerrainGenerator {

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
        Dice dice = new Dice(randomNumberGenerator);
        HexBoard hexBoard = new HexBoard(dice);

        HexPanel hexPanel = new HexPanel(hexBoard);
        JScrollPane scrollPane = new JScrollPane(hexPanel);

        JFrame f = new JFrame("Terrain Generator");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(scrollPane);
        f.setSize(500,500);
        f.setVisible(true);
    }


}