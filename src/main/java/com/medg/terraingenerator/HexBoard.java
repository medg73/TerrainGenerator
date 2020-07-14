package com.medg.terraingenerator;

import com.medg.terraingenerator.dice.Dice;
import com.medg.terraingenerator.hexlib.*;

import java.util.*;

public class HexBoard {

    private RectangularHexMap hexMap;
    private Layout layout;
    private int hexSize;
    private int hexMapHeight;
    private int hexMapWidth;
    private Orientation orientation = Orientation.LAYOUT_FLAT;
    private Point centerOfOriginHex;
    private Dice dice;

    private Set<RiverPair> allRivers;

    private Map<Hex, Integer> flowIntoHex;

//    private Map<Hex, Terrain> terrainMap;
    private Map<Hex, Integer> elevationMap;

    public HexBoard(Dice dice, int mapHeight, int mapWidth, int hexSize) {

        this.dice = dice;
        this.hexMapHeight = mapHeight;
        this.hexMapWidth = mapWidth;
        this.hexSize = hexSize;
        this.centerOfOriginHex = new Point(hexSize, hexSize);
        allRivers = new HashSet<>();
        flowIntoHex = new HashMap<>();

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

        Map<Hex, RiverPair> riverPairsByHighHex = new HashMap<>();
        Map<Hex, List<RiverPair>> riverPairsByLowHex = new HashMap<>();

        allRivers = new HashSet<>();
        flowIntoHex = new HashMap<>();
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
                RiverPair riverPair = new RiverPair(hex, steepestNeighbor, erosion);
                riverPairsByHighHex.put(hex, riverPair);
                List<RiverPair> riverPairs = riverPairsByLowHex.computeIfAbsent(steepestNeighbor, k -> new ArrayList<>());
                riverPairs.add(riverPair);
                allRivers.add(riverPair);
            }
        }

        List<Hex> riverSources = new ArrayList<>();
        for(RiverPair riverPair : allRivers) {
            Hex highHex = riverPair.getHighHex();
            if(riverPairsByLowHex.get(highHex) == null) { // river start
                riverSources.add(highHex);
            }
        }

        for(Hex sourceHex : riverSources) {
            RiverPair startingRiverPair = riverPairsByHighHex.get(sourceHex);
            Hex lowHex = startingRiverPair.getLowHex();
            RiverPair downstreamRiverPair = riverPairsByHighHex.get(lowHex);
            RiverPair upstreamRiverPair = startingRiverPair;
            while(downstreamRiverPair != null) {
                downstreamRiverPair.addUpstreamRiverPair(upstreamRiverPair);
                upstreamRiverPair.setDownstreamRiverPair(downstreamRiverPair);
                downstreamRiverPair.setFlow(downstreamRiverPair.getFlow() + upstreamRiverPair.getFlow());
                upstreamRiverPair = downstreamRiverPair;
                downstreamRiverPair = riverPairsByHighHex.get(downstreamRiverPair.getLowHex());
            }
        }
    }

    public int getFlowByRiverPair(RiverPair riverPair) {
        int rv = 0;
        for(RiverPair myRiverPair : allRivers) {
            if(myRiverPair.equals(riverPair)) {
                rv = myRiverPair.getFlow();
                break;
            }
        }
        return rv;
    }

    public Integer getFlowIntoHex(Hex hex) {
        int rv = 0;
        for(RiverPair riverPair : allRivers) {
            if(riverPair.getLowHex().equals(hex)) {
                rv += riverPair.getFlow();
            }
        }
        return rv;
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

    public Set<RiverPair> getAllRivers() {
        return allRivers;
    }

    public void setHexElevation(Hex hex, int elevation) {
        elevationMap.put(hex, elevation);
    }

    public Hex getHexByOffsetCoord(OffsetCoord offsetCoord) {
        return hexMap.getHex(offsetCoord);
    }
}
