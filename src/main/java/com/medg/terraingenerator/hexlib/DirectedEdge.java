package com.medg.terraingenerator.hexlib;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DirectedEdge {

    private final Hex source, sink;

    public DirectedEdge(Hex source, Hex sink) {
        assert(!source.equals(sink));
        List<Hex> neighbors = Arrays.asList(source.getAllNeighbors());
        assert(neighbors.contains(sink));
        this.source = source;
        this.sink = sink;
    }

    public Hex getSource() {
        return source;
    }

    public Hex getSink() {
        return sink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectedEdge that = (DirectedEdge) o;
        return Objects.equals(source, that.source) &&
                Objects.equals(sink, that.sink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, sink);
    }
}
