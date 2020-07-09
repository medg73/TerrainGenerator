package com.medg.terraingenerator.hexlib;

import java.util.HashSet;
import java.util.Set;

public class HexMap {

    private Set<Hex> hexes;
    private int mapWidth;
    private int mapHeight;
    private Layout layout;

    public HexMap(int mapWidth, int mapHeight, Layout layout) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.layout = layout;
        this.hexes = new HashSet<>();

        if(layout.orientation == Orientation.LAYOUT_POINTY) {
            for(int r = 0; r < mapHeight; r++) {
                int rOffset = (int)Math.floor(r / 2.0);
                for(int q = -rOffset; q < mapWidth - rOffset; q++) {
                    hexes.add(new Hex(q, r, -q - r));
                }
            }
        } else {
            for(int q = 0; q < mapHeight; q++) {
                int qOffset = (int)Math.floor(q / 2.0);
                for(int r = -qOffset; r < mapWidth - qOffset; r++) {
                    hexes.add(new Hex(q, r, -q - r));
                }
            }
        }

    }

    public Set<Hex> getHexes() {
        return this.hexes;
    }
}
