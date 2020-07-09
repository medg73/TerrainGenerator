package com.medg.terraingenerator.hexlib;

public enum Offset {
    EVEN(-1), ODD(1);

    public final int value;

    Offset(int value) {
        this.value = value;
    }
}
