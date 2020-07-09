package com.medg.terraingenerator;

import com.medg.terraingenerator.hexlib.FractionalHex;
import com.medg.terraingenerator.hexlib.Hex;
import com.medg.terraingenerator.hexlib.Layout;
import com.medg.terraingenerator.hexlib.Orientation;
import com.medg.terraingenerator.hexlib.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

class HexPanel extends JPanel {

    Set<Hex> hexes;
    Layout layout;
    int hexSize = 40;
    int hexMapHeight = 10;
    int hexMapWidth = 10;
    Orientation orientation = Orientation.LAYOUT_POINTY;
    Hex selectedHex1, selectedHex2;
    Set<Hex> highlightedHexes;
    Point centerOfOriginHex = new Point(hexSize, hexSize);

    public HexPanel() {
        setBackground(Color.WHITE);
        hexes = new HashSet<>();

        for(int r = 0; r < hexMapHeight; r++) {
            int rOffset = (int)Math.floor(r / 2.0);
            for(int q = -rOffset; q < hexMapWidth - rOffset; q++) {
                hexes.add(new Hex(q, r, -q - r));
            }
        }

//        for(int q = -3; q <= 3; q++) {
//            for(int r = -3; r <= 3; r++) {
//                for(int s = -3; s <= 3; s++) {
//                    if(q + r + s == 0) {
//                        hexes.add(new Hex(q, r, s));
//                    }
//                }
//            }
//        }

        layout = new Layout(orientation, new com.medg.terraingenerator.hexlib.Point(hexSize,hexSize), centerOfOriginHex);

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

        for(Hex hex : hexes) {
            Color fillColor = Color.WHITE;
            if(hex.equals(selectedHex1) || hex.equals(selectedHex2) ||
                    (highlightedHexes != null && highlightedHexes.contains(hex))) {
                fillColor = Color.YELLOW;
            }
            drawHex(g, hex, fillColor);
        }

    }

    private void drawHex(Graphics g, Hex hex, Color fillColor) {
        com.medg.terraingenerator.hexlib.Point[] cornerPoints = hex.polygonCorners(layout);
        int[] xpoints = new int[6];
        int[] ypoints = new int[6];
        for(int i = 0; i < cornerPoints.length; i++) {
            xpoints[i] = (int)Math.round(cornerPoints[i].x);
            ypoints[i] = (int)Math.round(cornerPoints[i].y);
        }
        Polygon polygon = new Polygon(xpoints, ypoints, 6);
        g.setColor(fillColor);
        g.fillPolygon(polygon);
        g.setColor(Color.BLUE);
        g.drawPolygon(polygon);

        String hexCoords = hex.getQ() + "," + hex.getR()
                + "," + hex.getS();
        com.medg.terraingenerator.hexlib.Point centerPoint = hex.toPixel(layout);
        int centerX = (int)Math.round(centerPoint.x - (hexSize / 2));
        int centerY = (int)Math.round(centerPoint.y);
        g.drawString(hexCoords, centerX, centerY);
    }

    private void selectHex(int x, int y) {
        FractionalHex fractionalHex = layout.pixelToHex(new Point(x, y));
        Hex selectedHex = fractionalHex.round();
        System.out.println("selected hex is: " + selectedHex.getQ() + "," + selectedHex.getR() + "," + selectedHex.getS());
        if(selectedHex1 == null) {
            selectedHex1 = selectedHex;
        } else if(selectedHex2 == null) {
            selectedHex2 = selectedHex;
            highlightedHexes = new HashSet<>(Set.of(selectedHex1.linedraw(selectedHex2)));
        } else {
            selectedHex1 = null;
            selectedHex2 = null;
            highlightedHexes = null;
        }
        repaint();
    }


}
