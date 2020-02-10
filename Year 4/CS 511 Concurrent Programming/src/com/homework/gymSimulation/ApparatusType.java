package com.homework.gymSimulation;

public enum ApparatusType {
    LEGPRESSMACHINE(0), BARBELL(1) , HACKSQUATMACHINE(2) , LEGEXTENSIONMACHINE(3) , LEGCURLMACHINE(4) , LATPULLDOWNMACHINE(5) , PECDECKMACHINE(6) , CABLECROSSOVERMACHINE(7);

    public int apparatusId;
    public static final int SIZE = 8;

    ApparatusType(int i){
        this.apparatusId = i;
    }

}
