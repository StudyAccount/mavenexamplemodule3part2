package com.schoology.app;

/**
 * Created by User on 09.08.2016.
 */
public class Runner {
    public static void main(String[] args) {
        int[] n = {1, 2, 3, 4};
        int i = 2;
        System.out.println("Square summ: " + new SquareSumCounter().getSquareSum(n, i));
    }

}
