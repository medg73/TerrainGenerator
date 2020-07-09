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

    public FractionalHex pixelToHex(Point p) {
        Point pt = new Point((p.x - this.origin.x) / this.size.x,
                            (p.y  - this.origin.y) / this.size.y);
        double q = orientation.b0 * pt.x + orientation.b1 * pt.y;
        double r = orientation.b2 * pt.x + orientation.b3 * pt.y;
        return new FractionalHex(q, r, -q - r);
    }
}
