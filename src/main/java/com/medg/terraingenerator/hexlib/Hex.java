package com.medg.terraingenerator.hexlib;

import java.util.Objects;

/**
 * https://www.redblobgames.com/grids/hexagons/implementation.html
 */
public class Hex {
    // counterclockwise from "right"
    static final Hex[] DIRECTIONS = { new Hex(1, 0, -1), new Hex(1, -1, 0), new Hex(0, -1, 1),
            new Hex(-1, 0, 1), new Hex(-1, 1, 0), new Hex(0, 1, -1)};

    private final int q, r, s;

    public Hex(int q, int r, int s) {
        assert(q + r + s == 0);
        this.q = q;
        this.r = r;
        this.s = s;
    }

    public int getQ() {
        return q;
    }

    public int getR() {
        return r;
    }

    public int getS() {
        return s;
    }

    Hex add(Hex hex) {
        return new Hex(this.getQ() + hex.getQ(), this.getR() + hex.getR(), this.getS() + hex.getS());
    }

    Hex subtract(Hex hex) {
        return new Hex(this.getQ() - hex.getQ(), this.getR() - hex.getR(), this.getS() - hex.getS());
    }

    Hex multiply(int k) {
        return new Hex(this.getQ() * k, this.getR() * k, this.getS() * k);
    }

    // adjacent hexagons are 2 apart in cube grid
    int distance(Hex hex) {
        int qDiff = Math.abs(this.getQ() - hex.getQ());
        int rDiff = Math.abs(this.getR() - hex.getR());
        int sDiff = Math.abs(this.getS() - hex.getS());
        return (qDiff + rDiff + sDiff) / 2;
    }

    public Hex[] getAllNeighbors() {
        Hex[] neighbors = new Hex[6];
        for(int i = 0; i < 6; i++) {
            neighbors[i] = this.neighbor(i);
        }
        return neighbors;
    }

    Hex neighbor(int direction) {
        assert(direction >= 0 && direction < 6);
        return this.add(DIRECTIONS[direction]);
    }

    public Point toPixel(Layout layout) {
        double x = (layout.orientation.f0 * this.getQ() + layout.orientation.f1 * this.getR()) * layout.size.x;
        double y = (layout.orientation.f2 * this.getQ() + layout.orientation.f3 * this.getR()) * layout.size.y;
        return new Point(x + layout.origin.x, y + layout.origin.y);
    }

    public Point[] polygonCorners(Layout layout) {
        Point[] corners = new Point[6];
        Point center = toPixel(layout);
        for(int i = 0; i < 6; i++) {
            Point offset = hexCornerOffset(layout, i);
            corners[i] = new Point(center.x + offset.x,
                    center.y + offset.y);
        }
        return corners;
    }

    public Hex[] linedraw(Hex hex) {
        int distance = distance(hex);
        Hex[] results = new Hex[distance + 1];
        double step = 1.0 / Math.max(distance, 1);
        for(int i = 0; i <= distance; i++) {
            results[i] = hexLerp(this, hex, step * i).round();
        }
        return results;
    }

    private FractionalHex hexLerp(Hex a, Hex b, double t) {
        return new FractionalHex(lerp(a.getQ(), b.getQ(), t),
                                 lerp(a.getR(), b.getR(), t),
                                 lerp(a.getS(), b.getS(), t));
    }

    private double lerp(double a, double b, double t) {
        return a * (1-t) + b * t;
    }

    private Point hexCornerOffset(Layout layout, int corner) {
        Point size = layout.size;
        double angle = 2.0 * Math.PI * (layout.orientation.startAngle + corner) / 6;
        return new Point(size.x * Math.cos(angle), size.y * Math.sin(angle));

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hex hex = (Hex) o;
        return q == hex.q &&
                r == hex.r &&
                s == hex.s;
    }

    @Override
    public int hashCode() {
        return Objects.hash(q, r, s);
    }

    @Override
    public String toString() {
//        return "Hex{" +
//                "q=" + q +
//                ", r=" + r +
//                ", s=" + s +
//                '}';
        return q + "," + r + "," + s;
    }
}
