package com.medg.terraingenerator;

import com.medg.terraingenerator.hexlib.*;
import com.medg.terraingenerator.hexlib.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

class HexPanel extends JPanel {

    HexMap hexMap;
    Layout layout;
    int hexSize = 40;
    int hexMapHeight = 10;
    int hexMapWidth = 10;
    Orientation orientation = Orientation.LAYOUT_FLAT;
    Hex selectedHex1, selectedHex2;
    Set<Hex> highlightedHexes;
    Point centerOfOriginHex = new Point(hexSize, hexSize);

    public HexPanel() {
        setBackground(Color.WHITE);

        layout = new Layout(orientation, new com.medg.terraingenerator.hexlib.Point(hexSize,hexSize), centerOfOriginHex);

        hexMap = new HexMap(hexMapWidth,hexMapHeight,layout);

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

        for(Hex hex : hexMap.getHexes()) {
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

//        String hexCoords = hex.getQ() + "," + hex.getR()
//                + "," + hex.getS();
        com.medg.terraingenerator.hexlib.Point centerPoint = hex.toPixel(layout);
        int centerX = (int)Math.round(centerPoint.x - (hexSize / 2));
        int centerY = (int)Math.round(centerPoint.y);
        g.drawString(hex.toString(), centerX, centerY);
    }

    private void selectHex(int x, int y) {
        FractionalHex fractionalHex = layout.pixelToHex(new Point(x, y));
        Hex selectedHex = fractionalHex.round();
        System.out.println("selected hex is: " + selectedHex);
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
