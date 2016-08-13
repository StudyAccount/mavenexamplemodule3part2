package com.schoology.app;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * Created by User on 07.07.2016.
 */
public class SquareSumCounter implements SquareSum {

    @Override
    public long getSquareSum(int[] values, int numberOfThreads) {

        int[] attributes = attributesCalculator(values, numberOfThreads);
        int parties = attributes[0];
        int capacity = attributes[1];

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        Phaser phaser = new Phaser(parties + 1);

        CopyOnWriteArrayList<Long> tempResults = new CopyOnWriteArrayList<>();

        long result = 0;
        int startIndex = 0;
        int endIndex = capacity;

        if (numberOfThreads > values.length){

            numberOfThreads = values.length;
        }

        for (int i = 0; i < numberOfThreads; i++){

            singleThreadStarter(values, executorService, phaser, tempResults, startIndex, endIndex);
            startIndex += capacity;
            endIndex += capacity;
        }

        phaser.arriveAndAwaitAdvance();
        executorService.shutdown();

        for (long sums:tempResults) {

            result += sums;
        }

        return result;
    }

    private void singleThreadStarter(int[] values, ExecutorService executorService, Phaser phaser,
                                     CopyOnWriteArrayList<Long> tempResults, int startIndex, int endIndex) {
        executorService.execute(() -> {
            long sum = 0;
            int j = startIndex;

            while (j < endIndex){

                sum+= Math.pow(values[j], 2);
                j++;
            }

            tempResults.add(sum);
            phaser.arrive();
        });
    }

    private int[] attributesCalculator(int[] values, int numberOfThreads){

        int parties;
        int capacity;
        int[] attributes = new int[2];

        if (values.length >= numberOfThreads){

            parties = numberOfThreads;
            capacity = values.length/numberOfThreads;
        } else {

            parties = values.length;
            capacity = 1;
        }
        attributes[0] = parties;
        attributes[1] = capacity;

        return attributes;
    }

}
