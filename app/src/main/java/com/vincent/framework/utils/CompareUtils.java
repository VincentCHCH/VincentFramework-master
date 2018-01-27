package com.vincent.framework.utils;

/**
 * Created by h234385 on 17/8/2017.
 */

public class CompareUtils {

    public static final double EPSILON = 0.0000001;

    public static boolean compareIntWithDouble (int firstNum, double SecondNum) {
        return Math.abs(firstNum - SecondNum) < EPSILON;
    }

    public static boolean compareFloatWithFloat (float firstNum, float SecondNum) {
        return Math.abs(firstNum - SecondNum) < EPSILON;
    }

    public static boolean compareIntWithFloat (int firstNum, float SecondNum) {
        return Math.abs(firstNum - SecondNum) < EPSILON;
    }

}
