package com.medg.terraingenerator;

import com.medg.terraingenerator.hexlib.Hex;

import java.util.*;

public class RiverPair {

    private final Hex highHex, lowHex;
    private int flow = 0;
    List<RiverPair> upstreamRiverPairs;
    RiverPair downstreamRiverPair;

    public RiverPair(Hex highHex, Hex lowHex) {
        List<Hex> neighbors = Arrays.asList(highHex.getAllNeighbors());
        assert(!highHex.equals(lowHex));
        assert(neighbors.contains(lowHex));

        this.highHex = highHex;
        this.lowHex = lowHex;
    }

    public RiverPair(Hex highHex, Hex lowHex, int flow) {
        this(highHex, lowHex);
        this.flow = flow;
    }

    public Hex getHighHex() {
        return highHex;
    }

    public Hex getLowHex() {
        return lowHex;
    }

    public Hex[] getHexArray() {
        Hex[] hexes = new Hex[2];
        hexes[0] = highHex;
        hexes[1] = lowHex;
        return hexes;
    }

    public Set<Hex> getHexes() {
        Set<Hex> hexSet = new HashSet<>(2);
        hexSet.add(highHex);
        hexSet.add(lowHex);
        return hexSet;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int newFlow) {
        this.flow = newFlow;
    }

    public List<RiverPair> getUpstreamRiverPairs() {
        return upstreamRiverPairs;
    }

    public RiverPair getDownstreamRiverPair() {
        return downstreamRiverPair;
    }

    public void addUpstreamRiverPair(RiverPair riverPair) {
        if(upstreamRiverPairs == null) {
            upstreamRiverPairs = new ArrayList<>();
        }
        upstreamRiverPairs.add(riverPair);
    }

    public void setDownstreamRiverPair(RiverPair riverPair) {
        downstreamRiverPair = riverPair;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RiverPair riverPair = (RiverPair) o;
        return this.getHexes().equals(riverPair.getHexes());
    }

    @Override
    public int hashCode() {
        return this.getHexes().hashCode();
    }
}
