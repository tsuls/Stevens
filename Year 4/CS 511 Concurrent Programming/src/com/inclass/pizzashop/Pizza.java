package com.inclass.pizzashop;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pizza {

    public static class PizzaShop {
        private Lock lock  = new ReentrantLock();
        Condition smallPizzaAvail = lock.newCondition();
        Condition largePizzaAvail = lock.newCondition();
        int smallPizza = 0;
        int largePizza = 0;

        public void buyLargePizza(){
            lock.lock();
            try {
                while (largePizza == 0 && smallPizza < 2) {
                    System.out.println(Thread.currentThread().getName() + "-> buyLargePizza -> waiting");
                    System.out.println("State: large " + largePizza + ". small " + smallPizza);
                    largePizzaAvail.await();
                }

                System.out.println(Thread.currentThread().getName() + "-> buyLargePizza -> buying");
                System.out.println("State: large " + largePizza + ". small " + smallPizza);

                if (largePizza > 0) {
                    largePizza--;
                } else {
                    smallPizza -= 2;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                lock.unlock();
            }
        }


        public void buySmallPizza() {
            lock.lock();
            try {
                while (smallPizza == 0) {
                    System.out.println(Thread.currentThread().getName() + "-> buySmallPizza -> waiting");
                    System.out.println("State: large " + largePizza + ". small " + smallPizza);

                    smallPizzaAvail.await();

                }

                System.out.println(Thread.currentThread().getName() + "-> buySmallPizza -> buying");
                System.out.println("State: large " + largePizza + ". small " + smallPizza);

                smallPizza--;
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                lock.unlock();
            }
        }

        public void bakeLargePizza() {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "-> bakeLargePizza -> baking");
            System.out.println("State: large " + largePizza + ". small " + smallPizza);
            largePizza++;
            largePizzaAvail.signal();
            lock.unlock();
        }

        public void bakeSmallPizza() {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "-> bakeSmallPizza -> baking");
            System.out.println("State: large " + largePizza + ". small " + smallPizza);
            smallPizza++;
            smallPizzaAvail.signal();
            largePizzaAvail.signal();
            lock.unlock();
        }

    }

    public static class Agent implements Runnable {
        private int type;
        private PizzaShop ps;

        Agent(int type, PizzaShop ps) {
            this.type=type;
            this.ps=ps;
        }

        public void run() {
            try {
                Thread.sleep(new Random().nextInt(1500));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            switch (type) {
                case 0 :
                    System.out.println(Thread.currentThread().getName()+"-> buyLargePizza");
                    ps.buyLargePizza();
                    break;
                case 1:
                    System.out.println(Thread.currentThread().getName()+"-> buySmallPizza");
                    ps.buySmallPizza();
                    break;
                case 2 :
                    System.out.println(Thread.currentThread().getName()+"-> bakeLargePizza");
                    ps.bakeLargePizza();
                    break;
                default:
                    System.out.println(Thread.currentThread().getName()+"-> bakeSmallPizza");
                    ps.bakeSmallPizza();
                    break;
            }
        }
    }

    public static void main(String[] args) {
        PizzaShop ps = new PizzaShop();
        final int N=1000;
        Random r = new Random();
        // 0 -> buyLargePizza
        // 1 -> buySmallPizza
        // 2 -> bakeLargePizza
        // 3 -> bakeSmallPizza


        for (int i=0; i<N; i++) {
            new Thread(new Agent(r.nextInt(4),ps)).start();
        }

    }
}


