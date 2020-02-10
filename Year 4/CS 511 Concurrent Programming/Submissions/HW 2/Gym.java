package com.homework.gymSimulation;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Gym implements Runnable{
    private static final int GYM_SIZE = 30;
    private static final int GYM_REGISTERED_CLIENTS = 10000;
    private static final int APPARATUS_LIMIT = 5;


    private Map<WeightPlateSize,Integer> noOfWeightPlates;
    private Set<Integer> clients; // for generating fresh client ids
    private ExecutorService executor;
    // various semaphores
    public static Semaphore[] apparatusPermits;
    public static Semaphore[] weightPermits;

    public Gym(){
        //Limit the number of Clients on the simulation
        this.executor = Executors.newFixedThreadPool(GYM_SIZE);
        this.noOfWeightPlates = initWeightMap();
        this.clients = new HashSet<Integer>();

        apparatusPermits = new Semaphore[]{
                new Semaphore(5),
                new Semaphore(5),
                new Semaphore(5),
                new Semaphore(5),
                new Semaphore(5),
                new Semaphore(5),
                new Semaphore(5),
                new Semaphore(5)
        };

        weightPermits =  weightPermits = new Semaphore[]{
                new Semaphore(noOfWeightPlates.get(WeightPlateSize.SMALL_3KG)),
                new Semaphore(noOfWeightPlates.get(WeightPlateSize.MEDIUM_5KG)),
                new Semaphore(noOfWeightPlates.get(WeightPlateSize.LARGE_10KG))
        };
    }

    /**
     * Opens the Gym and creates the Clients. The Clients begin their Routines
     */
    public void run(){
        System.out.println("Begin Gym Simulation");
        AtomicInteger uniqueIdCounter = new AtomicInteger();
        int id;
        Random r = new Random();
        for(int i  = 0; i < GYM_REGISTERED_CLIENTS; i++){
            //Lets generate random ids. We can use an incremental counter since they are only unique to this run
            id = uniqueIdCounter.getAndIncrement();

            //Add the id to the set
            this.clients.add(id);

            //Generate the client
            Client client =  Client.generateRandom(id);
            //Have the Client run his routine
            executor.execute(client);
        }
        System.out.println(clients.size());
        //Close
        executor.shutdown();
        //System.out.println("End Gym Simulation");
    }

    /**
     *Initializes the Map of the WeightPlates
     *
     * @return an initialized Map of the WeightPlates based on Assignment Spec
     */
    public static Map<WeightPlateSize,Integer> initWeightMap(){
        Map<WeightPlateSize, Integer> toRtn = new HashMap<WeightPlateSize, Integer>();

        toRtn.put(WeightPlateSize.SMALL_3KG, 110);
        toRtn.put(WeightPlateSize.MEDIUM_5KG, 90);
        toRtn.put(WeightPlateSize.LARGE_10KG, 75);

        return toRtn;
    }
}
