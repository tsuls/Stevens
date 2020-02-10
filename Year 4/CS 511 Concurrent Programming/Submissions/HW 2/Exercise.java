package com.homework.gymSimulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Exercise {
    private ApparatusType at;
    private Map<WeightPlateSize, Integer> weight;
    private int duration;

    public Exercise(ApparatusType at, Map<WeightPlateSize, Integer> weight, int duration){
        this.at = at;
        this.weight = weight;
        this.duration = duration;
    }

    /**
     * generates a random Exercise
     * @return the random Exercise
     */
    public static Exercise generateRandom(){
        ApparatusType[] theAppTypes = ApparatusType.values();
        Map<WeightPlateSize, Integer> wg = new HashMap<>();
        //Lets pick a random one
        Random r = new Random();
        int select = r.nextInt(((theAppTypes.length - 1) - 1) + 1) + 1;

        ApparatusType a = theAppTypes[select];

        //Get the number of weights
        Random w = new Random();
        int numWeights = w.nextInt(10);
        wg.put(WeightPlateSize.SMALL_3KG, numWeights);

        numWeights = w.nextInt(10);
        wg.put(WeightPlateSize.MEDIUM_5KG, numWeights);

        numWeights = w.nextInt(10);
        wg.put(WeightPlateSize.LARGE_10KG, numWeights);

        //A duration of 2-5 minutes
        int dur = r.nextInt((5000-2000) + 1) + 2000;

        return new Exercise(a, wg, dur);
    }

    /**
     * gets the Exercise's weight map
     * @return the Exercise's weight map
     */
    public Map<WeightPlateSize, Integer> getWeights(){
        return this.weight;
    }

    /**
     * gets the Exercise's duration
     * @return the Exercise's duration
     */
    public int getDuration(){
        return this.duration;
    }

    /**
     * gets the Exercise's Apparatus Type
     * @return the Exercise's Apparatus Type
     */
    public ApparatusType getApparatusType(){
        return this.at;
    }

    /**
     * gets a String representation of the Exercise
     * @return a String representation of the Exercise
     */
    public String toString() {
        return "Using Apparatus: " + this.at.toString() + " with the Weight set of: " + this.weight.toString() + " and a duration of: " + this.duration;
    }
}
