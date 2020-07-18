package com.medg.terraingenerator.hexboard;

import com.medg.terraingenerator.dice.Dice;
import com.medg.terraingenerator.hexlib.*;

import java.util.*;

public class HexBoard {

    private RectangularHexMap hexMap;
    private Layout layout;
    private int hexSize;
    private int hexMapHeight;
    private int hexMapWidth;
    private Orientation orientation;
    private Point centerOfOriginHex;
    private Dice dice;

    private Set<DirectedEdge> allRiverEdges;
    private Map<DirectedEdge, Integer> flowByEdge;

//    private Map<Hex, Terrain> terrainMap;
    private Map<Hex, Integer> elevationMap;

    HexBoard(Dice dice, int mapHeight, int mapWidth, int hexSize, Orientation orientation) {
        this.dice = dice;
        this.hexMapHeight = mapHeight;
        this.hexMapWidth = mapWidth;
        this.hexSize = hexSize;
        this.orientation = orientation;
        this.centerOfOriginHex = new Point(hexSize, hexSize);
        allRiverEdges = new HashSet<>();
        flowByEdge = new HashMap<>();

        layout = new Layout(orientation, new com.medg.terraingenerator.hexlib.Point(hexSize,hexSize), centerOfOriginHex);
        hexMap = new RectangularHexMap(hexMapHeight, hexMapWidth, layout);

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

        Map<Hex, DirectedEdge> riverEdgesByHighHex = new HashMap<>();
        Map<Hex, List<DirectedEdge>> riverEdgesByLowHex = new HashMap<>();

        allRiverEdges = new HashSet<>();
        flowByEdge = new HashMap<>();

        for(Hex hex : hexMap.getHexes()) {
            int hexHeight = elevationMap.get(hex);
            Map<Hex, Integer> elevationDiffs = getElevationDiffWithNeighbors(hex);
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
                DirectedEdge riverPair = new DirectedEdge(hex, steepestNeighbor);
                flowByEdge.put(riverPair, erosion);
                riverEdgesByHighHex.put(hex, riverPair);
                List<DirectedEdge> riverPairs = riverEdgesByLowHex.computeIfAbsent(steepestNeighbor, k -> new ArrayList<>());
                riverPairs.add(riverPair);
                allRiverEdges.add(riverPair);
            }
        }

        List<Hex> riverSources = findRiverSources(riverEdgesByLowHex);

        calculateRiverFlowByEdge(riverEdgesByHighHex, riverSources);
    }

    private void calculateRiverFlowByEdge(Map<Hex, DirectedEdge> riverEdgesByHighHex, List<Hex> riverSources) {
        for(Hex sourceHex : riverSources) {
            DirectedEdge startingRiverEdge = riverEdgesByHighHex.get(sourceHex);
            Hex lowHex = startingRiverEdge.getSink();
            DirectedEdge downstreamRiverEdge = riverEdgesByHighHex.get(lowHex);
            DirectedEdge upstreamRiverEdge = startingRiverEdge;
            while(downstreamRiverEdge != null) {
                int flow = flowByEdge.get(downstreamRiverEdge);
                flowByEdge.put(downstreamRiverEdge, flow + flowByEdge.get(upstreamRiverEdge));
                upstreamRiverEdge = downstreamRiverEdge;
                downstreamRiverEdge = riverEdgesByHighHex.get(downstreamRiverEdge.getSink());
            }
        }
    }

    private List<Hex> findRiverSources(Map<Hex, List<DirectedEdge>> riverEdgesByLowHex) {
        List<Hex> riverSources = new ArrayList<>();
        for(DirectedEdge riverEdge : allRiverEdges) {
            Hex highHex = riverEdge.getSource();
            if(riverEdgesByLowHex.get(highHex) == null) { // river start
                riverSources.add(highHex);
            }
        }
        return riverSources;
    }

    private Map<Hex, Integer> getElevationDiffWithNeighbors(Hex hex) {
        Hex[] neighbors = hex.getAllNeighbors();
        Map<Hex, Integer> elevationDiffs = new HashMap<>();
        for(int i = 0; i < neighbors.length; i++) {
            if(elevationMap.containsKey(neighbors[i])) {
                int elevationDiff = elevationMap.get(hex) - elevationMap.get(neighbors[i]);
                elevationDiffs.put(neighbors[i], elevationDiff);
            }
        }
        return elevationDiffs;
    }

    public int getRiverFlowByEdge(DirectedEdge riverPair) {
        int rv = 0;
        for(DirectedEdge myRiverPair : allRiverEdges) {
            if(myRiverPair.equals(riverPair)) {
                rv = flowByEdge.get(riverPair);
                break;
            }
        }
        return rv;
    }

    public Integer getFlowIntoHex(Hex hex) {
        int rv = 0;
        for(DirectedEdge riverPair : allRiverEdges) {
            if(riverPair.getSink().equals(hex)) {
                rv += flowByEdge.get(riverPair);
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

    public Set<DirectedEdge> getAllRiverEdges() {
        return allRiverEdges;
    }

    public void setHexElevation(Hex hex, int elevation) {
        if(hexMap.getHexes().contains(hex)) {
            elevationMap.put(hex, elevation);
        }
    }

    public Hex getHexByOffsetCoord(OffsetCoord offsetCoord) {
        return hexMap.getHex(offsetCoord);
    }

    public OffsetCoord getOffsetCoord(Hex hex) {
        return hexMap.getOffsetCoord(hex);
    }

    public Set<Hex> getHexes() {
        return hexMap.getHexes();
    }
}
