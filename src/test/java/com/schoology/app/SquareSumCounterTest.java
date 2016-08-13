package com.schoology.app;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by User on 12.07.2016.
 */
@RunWith(value = Parameterized.class)
public class SquareSumCounterTest {
    private static int[] values;
    private static int numberOfThreads;
    private static long expectation;
    private static final SquareSumCounter squareSum = new SquareSumCounter();


    public SquareSumCounterTest(int[] values, int numberOfThreads, long expectation ) {

        this.values = values;
        this.numberOfThreads = numberOfThreads;
        this.expectation = expectation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        int[] a = {5, 5, 3, 4, 5, 6, 7, 8, 9};
        int[] b = {0, 0, 1};
        int[] c = {3, -1, -4};
        int[] d = {3, -1};
        return Arrays.asList(new Object[][]{
                {a, 3, 330},
                {b, 1, 1},
                {c, 3, 26},
                {d, 3, 10},
        });

    }

    @Test
    public void testGetSquareSum() throws Exception {

        final long result = squareSum.getSquareSum(values, numberOfThreads);

        Assert.assertEquals(expectation, result, 0.0001);

    }
}