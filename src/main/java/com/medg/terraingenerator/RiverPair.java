package com.medg.terraingenerator;

import com.medg.terraingenerator.hexlib.Hex;

import java.util.*;

public class RiverPair {

    private final Hex[] hexes;

    public RiverPair(Hex hex1, Hex hex2) {
        hexes = new Hex[2];
        List<Hex> neighbors = Arrays.asList(hex1.getAllNeighbors());
        assert(neighbors.contains(hex2));
        assert(!hex1.equals(hex2));

        hexes[0] = hex1;
        hexes[1] = hex2;
    }

    public Hex[] getHexArray() {
        return hexes;
    }

    public Set<Hex> getHexes() {
        Set<Hex> hexSet = new HashSet<>(2);
        hexSet.add(hexes[0]);
        hexSet.add(hexes[1]);
        return hexSet;
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
