package com.medg.terraingenerator.ui;

import com.medg.terraingenerator.HexBoard;
import com.medg.terraingenerator.River;
import com.medg.terraingenerator.RiverPair;
import com.medg.terraingenerator.hexlib.*;
import com.medg.terraingenerator.hexlib.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
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
    Map<Hex, java.awt.Point> centerPointMap;
    boolean showWaterLevel = false;
    int waterLevel = 25;
    double zoomFactor = 1.0;

    public HexPanel(HexBoard hexBoard) {
        setBackground(Color.WHITE);

        loadNewBoard(hexBoard);

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                selectHex(e.getX(),e.getY());
            }
        });
    }

    public void loadNewBoard(HexBoard hexBoard) {
        this.hexBoard = hexBoard;
        this.hexMap = hexBoard.getHexMap();
        this.hexMapWidth = hexBoard.getHexMapWidth();
        this.hexMapHeight = hexBoard.getHexMapHeight();
        this.hexSize = hexBoard.getHexSize();
        this.layout = hexBoard.getLayout();

        hexPolygonMap = new HashMap<>();
        storeHexPolygons();
        storeHexCenterPoints();
        highlightedHexes = null;
        selectedHex1 = null;
        selectedHex2 = null;
        repaint();
    }

    public Dimension getPreferredSize() {
        int width, height;
        if(layout.getOrientation().equals(Orientation.LAYOUT_POINTY)) {
            width = (int) (hexMapWidth * hexSize * 1.75 * zoomFactor);
            height = (int) (hexMapHeight * hexSize * 1.51 * zoomFactor);
        } else {
            width = (int) (hexMapWidth * hexSize * 1.51 * zoomFactor);
            height = (int) (hexMapHeight * hexSize * 1.75 * zoomFactor);
        }
        return new Dimension(width, height);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;
        AffineTransform affineTransform = graphics2D.getTransform();
        graphics2D.scale(zoomFactor, zoomFactor);

        for(Hex hex : hexMap.getHexes()) {
            Color fillColor = getFillColor(hex);
            Color edgeColor = fillColor;
            drawHex(graphics2D, hex, fillColor, edgeColor);
        }

        drawAllRivers(graphics2D);

        if(selectedHex1 != null) {
            drawHexOutline(graphics2D, selectedHex1, Color.YELLOW);
        }
        if(selectedHex2 != null) {
            drawHexOutline(graphics2D, selectedHex2, Color.YELLOW);
        }
        if(highlightedHexes != null) {
            for(Hex hex :highlightedHexes) {
                drawHexOutline(graphics2D, hex, Color.YELLOW);
            }
        }

        graphics2D.setTransform(affineTransform);

    }



    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        assert(waterLevel >= 0 && waterLevel <= 100);
        this.waterLevel = waterLevel;
    }

    public boolean getShowWaterLevel() {
        return showWaterLevel;
    }

    public void setShowWaterLevel(boolean showWaterLevel) {
        this.showWaterLevel = showWaterLevel;
    }

    public void setZoomFactor(int zoomFactor) {
        this.zoomFactor = 1.0 / zoomFactor;
    }


    private void drawAllRivers(Graphics2D graphics2D) {
        River allRivers = hexBoard.getAllRivers();
        Set<RiverPair> riverPairs = allRivers.getRiverPairSet();
        for(RiverPair riverPair : riverPairs) {
            Hex[] hexes = riverPair.getHexArray();
            java.awt.Point point1 = centerPointMap.get(hexes[0]);
            java.awt.Point point2 = centerPointMap.get(hexes[1]);

            graphics2D.setColor(Color.BLUE);
            graphics2D.drawLine(point1.x, point1.y, point2.x, point2.y);
        }
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

    private void storeHexCenterPoints() {
        centerPointMap = new HashMap<>();
        for (Hex hex : hexMap.getHexes()) {
            Point centerPoint = hex.toPixel(layout);
            int x = (int) Math.round(centerPoint.x);
            int y = (int) Math.round(centerPoint.y);
            java.awt.Point point = new java.awt.Point(x, y);
            centerPointMap.put(hex, point);
        }
    }

    private Color getFillColor(Hex hex) {

        int elevation = hexBoard.getElevation(hex);

        if(this.showWaterLevel) {
            if(elevation < this.waterLevel) {
                return Color.BLUE;
            }
        }
        Color fillColor = new Color(0, elevation * 2, 0);
        return fillColor;


//        if(hexBoard.getTerrain(hex).equals(Terrain.WATER)) {
//            return Color.BLUE;
//        } else {
//            return Color.GREEN;
//        }
    }

    private void drawHex(Graphics2D graphics2D, Hex hex, Color fillColor, Color edgeColor) {

        Polygon polygon = hexPolygonMap.get(hex);

        graphics2D.setColor(fillColor);
        graphics2D.fillPolygon(polygon);
        graphics2D.setColor(edgeColor);
        graphics2D.drawPolygon(polygon);

//        com.medg.terraingenerator.hexlib.Point centerPoint = hex.toPixel(layout);
//        int centerX = (int)Math.round(centerPoint.x - (hexSize / 2));
//        int centerY = (int)Math.round(centerPoint.y);
//        g.drawString(hexBoard.getElevation(hex) +"", centerX, centerY);
//        g.drawString(hex.toString(), centerX, centerY);
    }

    private void drawHexOutline(Graphics2D graphics2D, Hex hex, Color edgeColor) {
        Polygon polygon = hexPolygonMap.get(hex);
        graphics2D.setColor(edgeColor);
        graphics2D.drawPolygon(polygon);
    }

    private void selectHex(int x, int y) {
        int scaledX = (int)(x / zoomFactor);
        int scaledY = (int)(y / zoomFactor);
        FractionalHex fractionalHex = layout.pixelToHex(new Point(scaledX, scaledY));
        Hex selectedHex = fractionalHex.round();
//        System.out.println("selected hex is: " + selectedHex);
        OffsetCoord offsetCoord = hexMap.getOffsetCoord(selectedHex);
        if(hexMap.getHexes().contains(selectedHex)) {
            int elevation = hexBoard.getElevation(selectedHex);
            System.out.println("row = " + offsetCoord.row + " col = " + offsetCoord.col + " elevation = " + elevation);

            if (selectedHex1 == null) {
                selectedHex1 = selectedHex;
            } else if (selectedHex2 == null) {
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
}
