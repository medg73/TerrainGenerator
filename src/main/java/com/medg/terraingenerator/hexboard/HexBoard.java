package com.medg.terraingenerator.hexboard;

import com.medg.terraingenerator.dice.Dice;
import com.medg.terraingenerator.hexlib.*;

import java.util.*;

public class HexBoard {

    private final RectangularHexMap hexMap;
    private final Layout layout;
    private final int hexSize;
    private final int hexMapHeight;
    private final int hexMapWidth;
    private final Dice dice;

    private Set<DirectedEdge> allRiverEdges = new HashSet<>();
    private Map<DirectedEdge, Integer> flowByEdge = new HashMap<>();
    Map<Hex, DirectedEdge> riverEdgesByHighHex = new HashMap<>();
    Map<Hex, List<DirectedEdge>> riverEdgesByLowHex = new HashMap<>();
    private Map<Hex, Integer> elevationMap;

    HexBoard(Dice dice, int mapHeight, int mapWidth, int hexSize, Orientation orientation) {
        this.dice = dice;
        this.hexMapHeight = mapHeight;
        this.hexMapWidth = mapWidth;
        this.hexSize = hexSize;
        Point centerOfOriginHex = new Point(hexSize, hexSize);

        layout = new Layout(orientation, new com.medg.terraingenerator.hexlib.Point(hexSize,hexSize), centerOfOriginHex);
        hexMap = new RectangularHexMap(hexMapHeight, hexMapWidth, layout);

        elevationMap = new HashMap<>();
        for(Hex hex : this.getHexes()) {
            elevationMap.put(hex, 1);
        }
    }

    public void addRandomTerrain() {
        for(Hex hex : this.getHexes()) {
            int newElevation = dice.rollPercent();
            if(elevationMap.get(hex) < newElevation) {
                elevationMap.put(hex, newElevation);
            }
        }

        placeRivers();
    }

    public void weather() {

        if(riverEdgesByHighHex == null || riverEdgesByHighHex.size() == 0) {
            this.placeRivers();
        }

        for(Hex hex : this.getHexes()) {
            int currentElevation = elevationMap.get(hex);
            if(currentElevation > 1 && riverEdgesByHighHex.containsKey(hex)) {
                elevationMap.put(hex, currentElevation - 1);
            }
        }
        placeRivers();
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

    public Integer getElevation(Hex hex) {
        return elevationMap.get(hex);
    }

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

    public void placeMountain(Hex hex) {
        int elevation = 100;
        this.setHexElevation(hex, elevation);
        for(int radius = 1; radius < 10; radius++) {
            Hex[] ringHexes = hex.getRing(radius);
            int ringElevation = elevation - radius * 10;
            for(Hex ringHex : ringHexes) {
                Integer currentElevation = this.getElevation(ringHex);
                if(currentElevation != null && currentElevation < ringElevation) {
                    this.setHexElevation(ringHex, ringElevation);
                }
            }
        }

        this.placeRivers();
    }

    private Hex findSteepestNeighbor(Hex hex) {
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

        return steepestNeighbor;
    }

    private void placeRivers() {

        allRiverEdges = new HashSet<>();
        flowByEdge = new HashMap<>();
        riverEdgesByHighHex = new HashMap<>();
        riverEdgesByLowHex = new HashMap<>();

        for(Hex hex : this.getHexes()) {
            Hex steepestNeighbor = findSteepestNeighbor(hex);
            if(steepestNeighbor != null) {
                addOutwardFlowingRiverEdge(hex, steepestNeighbor);
            }
        }

        List<Hex> riverSources = findRiverSources();
        calculateRiverFlowByEdge(riverSources);
    }

    private void addOutwardFlowingRiverEdge(Hex hex, Hex steepestNeighbor) {
        int flow = 1;
        DirectedEdge riverPair = new DirectedEdge(hex, steepestNeighbor);
        flowByEdge.put(riverPair, flow);
        riverEdgesByHighHex.put(hex, riverPair);
        List<DirectedEdge> riverPairs = riverEdgesByLowHex.computeIfAbsent(steepestNeighbor, k -> new ArrayList<>());
        riverPairs.add(riverPair);
        allRiverEdges.add(riverPair);
    }

    private void calculateRiverFlowByEdge(List<Hex> riverSources) {
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

    private List<Hex> findRiverSources() {
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
}
