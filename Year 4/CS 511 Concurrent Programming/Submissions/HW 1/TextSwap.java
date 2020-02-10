package com.homework.textswap;

import java.io.*;
import java.util.*;

public class TextSwap {

    private static String readFile(String filename) throws Exception {
        String line;
        StringBuilder buffer = new StringBuilder();
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        br.close();
        return buffer.toString();
    }

    private static Interval[] getIntervals(int numChunks, int chunkSize) {
       Interval[] intervals = new Interval[numChunks];

       for(int i = 0; i < numChunks; i++) {
           int startLoc = i * chunkSize;
           int endLoc = startLoc + (chunkSize - 1);
           Interval interval = new Interval(startLoc,  endLoc);
           intervals[i] = interval;
       }

        return intervals;
    }

    private static List<Character> getLabels(int numChunks) {
        Scanner scanner = new Scanner(System.in);
        List<Character> labels = new ArrayList<Character>();
        int endChar = numChunks == 0 ? 'a' : 'a' + numChunks - 1;
        System.out.printf("Input %d character(s) (\'%c\' - \'%c\') for the pattern.\n", numChunks, 'a', endChar);
        for (int i = 0; i < numChunks; i++) {
            labels.add(scanner.next().charAt(0));
        }
        scanner.close();
        // System.out.println(labels);
        return labels;
    }

    private static char[] runSwapper(String content, int chunkSize, int numChunks) {
        List<Character> labels = getLabels(numChunks);
        Interval[] intervals = getIntervals(numChunks, chunkSize);
        Scanner scanner = new Scanner (System.in);
        char[] newBuffer = new char[chunkSize*numChunks];
        // TODO: Order the intervals properly, then run the Swapper instances.
        List<Thread> myThreadList = new ArrayList<Thread>();
        for(int i = 0; i < numChunks; i++){
            int offset = i * chunkSize;
            Swapper toRun = new Swapper(intervals[labels.get(i) - 'a'], content, newBuffer, offset);
            Thread theThread = new Thread(toRun);
            myThreadList.add(theThread);
            theThread.start();
        }
        //Wait for current thread to stop before continuing
        Boolean threadIsRunning = true;
        while(threadIsRunning) {
            for (Thread thread : myThreadList){
                if(thread.isAlive()) {
                    threadIsRunning = false;
                }
            }
        }
        return newBuffer;
    }

    private static void writeToFile(String contents, int chunkSize, int numChunks) throws Exception {
        char[] buff = runSwapper(contents, chunkSize, contents.length() / chunkSize);
        //Debug print statement for(int i = 0; i < buff.length; i++){ System.out.println(buff[i]);}
        PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
        writer.print(buff);
        writer.close();
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java TextSwap <chunk size> <filename>");
            return;
        }
        String contents = "";
        int chunkSize = Integer.parseInt(args[0]);
        try {
            contents = readFile(args[1]);
            writeToFile(contents, chunkSize, contents.length() / chunkSize);
        } catch (Exception e) {
            System.out.println("Error with IO.");
            e.printStackTrace();
            return;
        }
    }
}