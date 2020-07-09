package com.medg.terraingenerator.hexlib;

public class FractionalHex {

    final double q, r, s;

    public FractionalHex(double q, double r, double s) {
        this.q = q;
        this.r = r;
        this.s = s;
    }

    public Hex round() {
        int q = (int)Math.round(this.q);
        int r = (int)Math.round(this.r);
        int s = (int)Math.round(this.s);
        double qDiff = Math.abs(q - this.q);
        double rDiff = Math.abs(r - this.r);
        double sDiff = Math.abs(s - this.s);

        if(qDiff > rDiff && qDiff > sDiff) {
            q = -r - s;
        } else if(rDiff > sDiff) {
            r = -q - s;
        } else {
            s = -q - r;
        }

        return new Hex(q, r, s);
    }

}
