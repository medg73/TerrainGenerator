package com.medg.terraingenerator.hexlib;

import java.util.Objects;

public class OffsetCoord {

    public final int row, col;

    public OffsetCoord(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OffsetCoord that = (OffsetCoord) o;
        return row == that.row &&
                col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "OffsetCoord{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
