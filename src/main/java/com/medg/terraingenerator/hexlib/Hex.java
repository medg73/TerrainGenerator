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

    Hex(int q, int r, int s) {
        assert(q + r + s == 0);
        this.q = q;
        this.r = r;
        this.s = s;
    }

    int getQ() {
        return q;
    }

    int getR() {
        return r;
    }

    int getS() {
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

    Hex neighbor(int direction) {
        assert(direction >= 0 && direction < 6);
        return this.add(DIRECTIONS[direction]);
    }

    Point toPixel(Layout layout) {
        double x = (layout.orientation.f0 * this.getQ() + layout.orientation.f1 * this.getR()) * layout.size.x;
        double y = (layout.orientation.f2 * this.getQ() + layout.orientation.f3 * this.getR()) * layout.size.y;
        return new Point(x + layout.origin.x, y + layout.origin.y);
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
}
