package com.homework.gymSimulation;

import java.util.*;

public class Client implements Runnable{
    private int id;
    private List<Exercise> routine;

    public Client(int id){
        this.id = id;
        this.routine = new ArrayList<Exercise>();
    }

    /**
     * adds an exercise to the Client's routine
     * @param e the exercise to add
     */
    public void addExercise(Exercise e){
        this.routine.add(e);
    }

    /**
     * Generates a randomm client with the given id
     * @param id the Client's id
     * @return a new Client with a random routine with the given id
     */
    public static Client generateRandom(int id){
        Client toRtn = new Client(id);

        //15-20 random exercises
        Random r = new Random();
        int numOfExercises =r.nextInt((20 - 15) + 1) + 15;

        for(int i = 0; i < numOfExercises; i++){
            toRtn.addExercise(Exercise.generateRandom());
        }

        return toRtn;
    }

    /**
     * executes the Client's routine
     */
    @Override
    public void run(){
        for(int i = 0; i < routine.size(); i++){
            try {

                Exercise theExercise = routine.get(i);

                System.out.println(Thread.currentThread().getName() + ": Client " + this.id + " is " + theExercise.toString());

                //We need to acquire the machine necessary
                switch (theExercise.getApparatusType()){
                    case LEGPRESSMACHINE:
                        Gym.apparatusPermits[0].acquire();
                    case BARBELL:
                        Gym.apparatusPermits[1].acquire();
                    case HACKSQUATMACHINE:
                        Gym.apparatusPermits[2].acquire();
                    case LEGEXTENSIONMACHINE:
                        Gym.apparatusPermits[3].acquire();
                    case LEGCURLMACHINE:
                        Gym.apparatusPermits[4].acquire();
                    case LATPULLDOWNMACHINE:
                        Gym.apparatusPermits[5].acquire();
                    case PECDECKMACHINE:
                        Gym.apparatusPermits[6].acquire();
                    case CABLECROSSOVERMACHINE:
                        Gym.apparatusPermits[7].acquire();
                }

                System.out.println("just acquired machine");

                //Now we need to acquire the weights necessary
                Set<WeightPlateSize> theWeights = theExercise.getWeights().keySet(); //Get the WeightPlateSizes for this Exercise
                for(WeightPlateSize currentWeight : theWeights){
                    //Now look for the plates for each size
                    Map<WeightPlateSize, Integer> theWeightSet = theExercise.getWeights();
                    switch (currentWeight){
                        case SMALL_3KG:
                            //The specs require that we acquire for each individual weight
                            for(int j = 0; j < theWeightSet.get(currentWeight); j++){
                                Gym.weightPermits[0].acquire();
                            }
                        case MEDIUM_5KG:
                            for(int j = 0; j < theWeightSet.get(currentWeight); j++){
                                Gym.weightPermits[1].acquire();
                            }
                        case LARGE_10KG:
                            for(int j = 0; j < theWeightSet.get(currentWeight); j++){
                                Gym.weightPermits[2].acquire();
                            }
                    }
            }
                System.out.println("just acquired weights");

                //We now have acquired our weights and we now need to complete the exercise
                Thread.sleep(theExercise.getDuration()); //Thread is "completing" the exercise

                //Time to release the weights since we finished the exercise. Its basically the same as before only releasing rather than acquiring.
                switch (theExercise.getApparatusType()){
                    case LEGPRESSMACHINE:
                        Gym.apparatusPermits[0].release();
                    case BARBELL:
                        Gym.apparatusPermits[1].release();
                    case HACKSQUATMACHINE:
                        Gym.apparatusPermits[2].release();
                    case LEGEXTENSIONMACHINE:
                        Gym.apparatusPermits[3].release();
                    case LEGCURLMACHINE:
                        Gym.apparatusPermits[4].release();
                    case LATPULLDOWNMACHINE:
                        Gym.apparatusPermits[5].release();
                    case PECDECKMACHINE:
                        Gym.apparatusPermits[6].release();
                    case CABLECROSSOVERMACHINE:
                        Gym.apparatusPermits[7].release();
                }

                System.out.println("just released machine");
                for(WeightPlateSize currentWeight : theWeights){
                    //Now look for the plates for each size
                    Map<WeightPlateSize, Integer> theWeightSet = theExercise.getWeights();
                    switch (currentWeight){
                        case SMALL_3KG:
                            //The specs require that we release for each individual weight
                            for(int j = 0; j < theWeightSet.get(currentWeight); j++){
                                Gym.weightPermits[0].release();
                            }
                        case MEDIUM_5KG:
                            for(int j = 0; j < theWeightSet.get(currentWeight); j++){
                                Gym.weightPermits[1].release();
                            }
                        case LARGE_10KG:
                            for(int j = 0; j < theWeightSet.get(currentWeight); j++){
                                Gym.weightPermits[2].release();
                            }
                    }
                }
                System.out.println("just released weights");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
