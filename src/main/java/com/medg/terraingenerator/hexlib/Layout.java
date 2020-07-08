package com.medg.terraingenerator.hexlib;

public class Layout {

    final Orientation orientation;
    final Point size;
    final Point origin;

    public Layout(Orientation orientation, Point size, Point origin) {
        this.orientation = orientation;
        this.size = size;
        this.origin = origin;
    }
}
