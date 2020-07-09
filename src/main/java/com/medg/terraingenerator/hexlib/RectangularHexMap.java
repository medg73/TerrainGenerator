package com.medg.terraingenerator.hexlib;

import java.util.HashSet;
import java.util.Set;

public class RectangularHexMap {

    private Set<Hex> hexes;
    private int mapWidth;
    private int mapHeight;
    private Layout layout;

    public RectangularHexMap(int mapWidth, int mapHeight, Layout layout) {
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

    public OffsetCoord getOffsetCoord(Hex hex) {
        if(layout.orientation == Orientation.LAYOUT_FLAT) {
            return qoffsetFromCube(Offset.EVEN, hex);
        } else {
            return roffsetFromCube(Offset.EVEN, hex);
        }
    }

    public Hex getHex(OffsetCoord offsetCoord) {
        if(layout.orientation == Orientation.LAYOUT_FLAT) {
            return qoffsetToCube(Offset.EVEN, offsetCoord);
        } else {
            return roffsetToCube(Offset.EVEN, offsetCoord);
        }
    }

    private Hex roffsetToCube(Offset offset, OffsetCoord offsetCoord) {
        int q = offsetCoord.col - ((offsetCoord.row + offset.value * (Math.abs(offsetCoord.row) % 2)) / 2);
        int r = offsetCoord.row;
        int s = -q - r;
        return new Hex(q, r, s);
    }

    private Hex qoffsetToCube(Offset offset, OffsetCoord offsetCoord) {
        int q = offsetCoord.col;
        int r = offsetCoord.row - ((offsetCoord.col + offset.value * (Math.abs(offsetCoord.col) % 2)) / 2);
        int s = -q -r;
        return new Hex(q, r, s);
    }

    private OffsetCoord qoffsetFromCube(Offset offset, Hex hex) {
        int col = hex.getQ();
        int row = hex.getR() + ((hex.getQ() + offset.value * (Math.abs(hex.getQ()) % 2)) / 2);
        return new OffsetCoord(row, col);
    }

    private OffsetCoord roffsetFromCube(Offset offset, Hex hex) {
        int col = hex.getQ() + ((hex.getR() + offset.value * (Math.abs(hex.getR()) % 2)) / 2);
        int row = hex.getR();
        return new OffsetCoord(row, col);
    }
}
