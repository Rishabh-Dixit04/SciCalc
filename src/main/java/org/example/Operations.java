package org.example;
import java.lang.Math;

public class Operations {

    public static double squareRoot(double x){
        if (x < 0)
            throw new IllegalArgumentException("x must be >= 0");
        return Math.sqrt(x);
    }

    public static double factorial(double x){
        if (x < 0){
            throw new IllegalArgumentException("x must be >= 0");
        }
        if (x == 0)   return 1;
        return x * factorial(x - 1);
    }

    public static double log(double x){
        if (x <= 0){
            throw new IllegalArgumentException("x must be > 0");
        }
        return Math.log(x);
    }

    public static double pow(double x, double b){
        if (x == 0 && b <= 0) {
            throw new IllegalArgumentException("0 must be raised to a positive power.");
        }
        return Math.pow(x, b);
    }
}
