package com.medg.terraingenerator.hexlib;

import java.util.HashSet;
import java.util.Set;

public class HexagonHexMap {

    Set<Hex> hexes;

    public HexagonHexMap(int radius) {

        hexes = new HashSet<>();
        for(int q = -radius; q <= radius; q++) {
            for(int r = -radius; r <= radius; r++) {
                for(int s = -radius; s <= radius; s++) {
                    if(q + r + s == 0) {
                        hexes.add(new Hex(q, r, s));
                    }
                }
            }
        }
    }

    public Set<Hex> getHexes() {
        return hexes;
    }
}
