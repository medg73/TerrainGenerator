package com.medg.terraingenerator;

import com.medg.terraingenerator.hexlib.*;
import com.medg.terraingenerator.hexlib.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

class HexPanel extends JPanel {

    Set<Hex> hexes;
    Layout layout;
    int hexSize = 40;
    int center = 250;
    Orientation orientation = Orientation.LAYOUT_FLAT;
    Hex selectedHex;

    public HexPanel() {
        setBackground(Color.WHITE);
        hexes = new HashSet<>();

        for(int q = -3; q <= 3; q++) {
            for(int r = -3; r <= 3; r++) {
                for(int s = -3; s <= 3; s++) {
                    if(q + r + s == 0) {
                        hexes.add(new Hex(q, r, s));
                    }
                }
            }
        }
        selectedHex = new Hex(0, 0, 0);

        layout = new Layout(orientation, new Point(hexSize,hexSize), new Point(center, center));

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                selectHex(e.getX(),e.getY());
            }
        });
    }


    public Dimension getPreferredSize() {
        return new Dimension(500,500);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.setColor(Color.BLACK);
//        g.drawString("This is my custom Panel!",10,20);

        g.setColor(Color.BLUE);
        for(Hex hex : hexes) {
            Point[] cornerPoints = hex.polygonCorners(layout);
            int[] xpoints = new int[6];
            int[] ypoints = new int[6];
            for(int i = 0; i < cornerPoints.length; i++) {
                xpoints[i] = (int)Math.round(cornerPoints[i].x);
                ypoints[i] = (int)Math.round(cornerPoints[i].y);
            }
            Polygon polygon = new Polygon(xpoints, ypoints, 6);
            if(hex.equals(selectedHex)) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.WHITE);
            }
            g.fillPolygon(polygon);
            g.setColor(Color.BLUE);
            g.drawPolygon(polygon);

//            for(int i = 1; i < cornerPoints.length; i++) {
//                int startX = (int)Math.round(cornerPoints[i - 1].x);
//                int startY = (int)Math.round(cornerPoints[i - 1].y);
//                int endX = (int)Math.round(cornerPoints[i].x);
//                int endY = (int)Math.round(cornerPoints[i].y);
//                g.drawLine(startX, startY, endX, endY);
//            }
//            int startX = (int)Math.round(cornerPoints[5].x);
//            int startY = (int)Math.round(cornerPoints[5].y);
//            int endX = (int)Math.round(cornerPoints[0].x);
//            int endY = (int)Math.round(cornerPoints[0].y);
//            g.drawLine(startX, startY, endX, endY);

            String hexCoords = hex.getQ() + "," + hex.getR()
                    + "," + hex.getS();
            Point centerPoint = hex.toPixel(layout);
            int centerX = (int)Math.round(centerPoint.x - (hexSize / 2));
            int centerY = (int)Math.round(centerPoint.y);
            g.drawString(hexCoords, centerX, centerY);
        }

    }

    private void selectHex(int x, int y) {
        FractionalHex fractionalHex = layout.pixelToHex(new Point(x, y));
        Hex selectedHex = fractionalHex.round();
        System.out.println("selected hex is: " + selectedHex.getQ() + "," + selectedHex.getR() + "," + selectedHex.getS());
        this.selectedHex = selectedHex;
        repaint();
    }


}