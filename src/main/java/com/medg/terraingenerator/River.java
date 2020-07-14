package com.medg.terraingenerator;

import java.util.HashSet;
import java.util.Set;

public class River {

    private Set<RiverPair> riverPairSet;

    public River() {
        riverPairSet = new HashSet<>();
    }

    public Set<RiverPair> getRiverPairSet() {
        return riverPairSet;
    }

    public void add(RiverPair riverPair) {
        riverPairSet.add(riverPair);
    }
}
