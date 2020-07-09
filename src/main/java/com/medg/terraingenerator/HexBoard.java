package com.medg.terraingenerator;

import com.medg.terraingenerator.dice.Dice;
import com.medg.terraingenerator.hexlib.*;

import java.util.HashMap;
import java.util.Map;

public class HexBoard {

    private RectangularHexMap hexMap;
    private Layout layout;
    private int hexSize = 40;
    private int hexMapHeight = 100;
    private int hexMapWidth = 100;
    private Orientation orientation = Orientation.LAYOUT_POINTY;
    private Point centerOfOriginHex = new Point(hexSize, hexSize);
    private Dice dice;

    private Map<Hex, Terrain> terrainMap;

    public HexBoard(Dice dice) {

        this.dice = dice;

        layout = new Layout(orientation, new com.medg.terraingenerator.hexlib.Point(hexSize,hexSize), centerOfOriginHex);
        hexMap = new RectangularHexMap(hexMapWidth,hexMapHeight,layout);

        terrainMap = new HashMap<>();
        for(Hex hex : hexMap.getHexes()) {
            int elevation = dice.rollPercent();
            if(elevation < 25) {
                terrainMap.put(hex, Terrain.WATER);
            } else {
                terrainMap.put(hex, Terrain.LAND);
            }

        }
    }

    public RectangularHexMap getHexMap() {
        return hexMap;
    }

    public Terrain getTerrain(Hex hex) {
        return terrainMap.get(hex);
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
}
