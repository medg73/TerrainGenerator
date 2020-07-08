package com.medg.terraingenerator;

import javax.swing.*;
import java.awt.*;

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
        f.setSize(250,250);
        f.setVisible(true);
    }


}

class HexPanel extends JPanel {

    public HexPanel() {
        setBackground(Color.WHITE);
    }


    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("This is my custom Panel!",10,20);

    }
}