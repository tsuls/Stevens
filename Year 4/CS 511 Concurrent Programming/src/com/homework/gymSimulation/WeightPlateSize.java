package com.homework.gymSimulation;

public enum WeightPlateSize {
    SMALL_3KG(0), MEDIUM_5KG(1), LARGE_10KG(2);

    public int weightId;
    public static final int SIZE = 3;

    WeightPlateSize(int i){
        this.weightId = i;
    }
}
