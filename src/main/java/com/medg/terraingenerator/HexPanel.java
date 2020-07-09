package com.medg.terraingenerator;

import com.medg.terraingenerator.hexlib.*;
import com.medg.terraingenerator.hexlib.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class HexPanel extends JPanel {

    RectangularHexMap hexMap;
    HexBoard hexBoard;
    Layout layout;
    int hexSize;
    int hexMapHeight;
    int hexMapWidth;
    Hex selectedHex1, selectedHex2;
    Set<Hex> highlightedHexes;
    Map<Hex, Polygon> hexPolygonMap;

    public HexPanel(HexBoard hexBoard) {
        setBackground(Color.WHITE);

        this.hexBoard = hexBoard;
        hexMap = hexBoard.getHexMap();
        this.hexMapWidth = hexBoard.getHexMapWidth();
        this.hexMapHeight = hexBoard.getHexMapHeight();
        this.hexSize = hexBoard.getHexSize();
        this.layout = hexBoard.getLayout();

        hexPolygonMap = new HashMap<>();
        storeHexPolygons();

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                selectHex(e.getX(),e.getY());
            }
        });
    }

    private void storeHexPolygons() {
        for(Hex hex : hexMap.getHexes()) {
            Point[] cornerPoints = hex.polygonCorners(layout);
            int[] xpoints = new int[6];
            int[] ypoints = new int[6];
            for(int i = 0; i < cornerPoints.length; i++) {
                xpoints[i] = (int)Math.round(cornerPoints[i].x);
                ypoints[i] = (int)Math.round(cornerPoints[i].y);
            }
            Polygon polygon = new Polygon(xpoints, ypoints, 6);
            hexPolygonMap.put(hex, polygon);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(hexMapWidth * hexSize * 2,hexMapHeight * hexSize * 2);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(Hex hex : hexMap.getHexes()) {
            Color fillColor = getFillColor(hex);
            Color edgeColor = Color.BLUE;
            if(hex.equals(selectedHex1) || hex.equals(selectedHex2) ||
                    (highlightedHexes != null && highlightedHexes.contains(hex))) {
                edgeColor = Color.YELLOW;
            }
            drawHex(g, hex, fillColor, edgeColor);
        }

        if(selectedHex1 != null) {
            drawHex(g, selectedHex1, getFillColor(selectedHex1), Color.YELLOW);
        }
        if(selectedHex2 != null) {
            drawHex(g, selectedHex2, getFillColor(selectedHex2), Color.YELLOW);
        }
        if(highlightedHexes != null) {
            for(Hex hex :highlightedHexes) {
                drawHex(g, hex, getFillColor(hex), Color.YELLOW);
            }
        }

    }

    private Color getFillColor(Hex hex) {
        if(hexBoard.getTerrain(hex).equals(Terrain.WATER)) {
            return Color.BLUE;
        } else {
            return Color.GREEN;
        }
    }

    private void drawHex(Graphics g, Hex hex, Color fillColor, Color edgeColor) {

        Polygon polygon = hexPolygonMap.get(hex);
        g.setColor(fillColor);
        g.fillPolygon(polygon);
        g.setColor(edgeColor);
        g.drawPolygon(polygon);

//        com.medg.terraingenerator.hexlib.Point centerPoint = hex.toPixel(layout);
//        int centerX = (int)Math.round(centerPoint.x - (hexSize / 2));
//        int centerY = (int)Math.round(centerPoint.y);
//        g.drawString(hex.toString(), centerX, centerY);
    }

    private void selectHex(int x, int y) {
        FractionalHex fractionalHex = layout.pixelToHex(new Point(x, y));
        Hex selectedHex = fractionalHex.round();
//        System.out.println("selected hex is: " + selectedHex);
        OffsetCoord offsetCoord = hexMap.getOffsetCoord(selectedHex);
        System.out.println("row = " + offsetCoord.row + " col = " + offsetCoord.col);

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
