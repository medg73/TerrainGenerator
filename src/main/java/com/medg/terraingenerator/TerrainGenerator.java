package com.medg.terraingenerator;

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
        JFrame f = new JFrame("Terrain Generator");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new HexPanel());
        f.setSize(500,500);
        f.setVisible(true);
    }


}