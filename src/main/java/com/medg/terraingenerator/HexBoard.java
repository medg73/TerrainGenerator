package com.medg.terraingenerator;

import com.medg.terraingenerator.dice.Dice;
import com.medg.terraingenerator.hexlib.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HexBoard {

    private RectangularHexMap hexMap;
    private Layout layout;
    private int hexSize;
    private int hexMapHeight;
    private int hexMapWidth;
    private Orientation orientation = Orientation.LAYOUT_FLAT;
    private Point centerOfOriginHex;
    private Dice dice;
    private River allRivers;

//    private Map<Hex, Terrain> terrainMap;
    private Map<Hex, Integer> elevationMap;

    public HexBoard(Dice dice, int mapHeight, int mapWidth, int hexSize) {

        this.dice = dice;
        this.hexMapHeight = mapHeight;
        this.hexMapWidth = mapWidth;
        this.hexSize = hexSize;
        this.centerOfOriginHex = new Point(hexSize, hexSize);
        allRivers = new River();

        layout = new Layout(orientation, new com.medg.terraingenerator.hexlib.Point(hexSize,hexSize), centerOfOriginHex);
        hexMap = new RectangularHexMap(hexMapWidth,hexMapHeight,layout);

        elevationMap = new HashMap<>();
        for(Hex hex : hexMap.getHexes()) {
            int elevation = dice.rollPercent();
            elevationMap.put(hex, elevation);
//            System.out.println(hexMap.getOffsetCoord(hex).toString() + " elevation is: " + elevation);
//            if(elevation < 25) {
//                terrainMap.put(hex, Terrain.WATER);
//            } else {
//                terrainMap.put(hex, Terrain.LAND);
//            }

        }
    }

    public void weather() {

        allRivers = new River();
        for(Hex hex : hexMap.getHexes()) {
            int hexHeight = elevationMap.get(hex);
            Hex[] neighbors = hex.getAllNeighbors();
            Map<Hex, Integer> elevationDiffs = new HashMap<>();
            for(int i = 0; i < neighbors.length; i++) {
                if(elevationMap.keySet().contains(neighbors[i])) {
                    int elevationDiff = elevationMap.get(hex) - elevationMap.get(neighbors[i]);
                    elevationDiffs.put(neighbors[i], elevationDiff);
                }
            }
            int steepestSlope = 0;
            Hex steepestNeighbor = null;
            for(Hex neighbor : elevationDiffs.keySet()) {
                int elevationDiff = elevationDiffs.get(neighbor);
                if(elevationDiff > 0 && elevationDiff > steepestSlope) {
                    steepestSlope = elevationDiff;
                    steepestNeighbor = neighbor;
                }
            }

            if(steepestSlope > 0) {
                int erosion = dice.rollD10();
                if(erosion > steepestSlope) {
                    erosion = steepestSlope;
                }
                elevationMap.put(hex, hexHeight - erosion);
            }
            if(steepestNeighbor != null) {
                allRivers.add(new RiverPair(hex, steepestNeighbor));
            }
        }
    }

    public RectangularHexMap getHexMap() {
        return hexMap;
    }

    public Integer getElevation(Hex hex) {
        return elevationMap.get(hex);
    }

//    public Terrain getTerrain(Hex hex) {
//        return terrainMap.get(hex);
//    }

    public int getHexMapHeight() {
        return hexMapHeight;
    }

    public int getHexMapWidth() {
        return hexMapWidth;
    }

    public int getHexSize() {
        return hexSize;
    }

    public Layout getLayout() {
        return layout;
    }

    public River getAllRivers() {
        return allRivers;
    }
}
