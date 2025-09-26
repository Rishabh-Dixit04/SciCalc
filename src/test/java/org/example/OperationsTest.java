package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {

    @Test
    void testSquareRootPositive() {
        double result = Operations.squareRoot(9);
        assertEquals(3.0, result);
    }

    @Test
    void testSquareRootNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            Operations.squareRoot(-5);
        });
    }

    @Test
    void testFactorialValid() {
        double result = Operations.factorial(5);
        assertEquals(120, result);
    }


    @Test
    void testFactorialNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            Operations.factorial(-5);
        });
    }

    @Test
    void testLogValid() {
        double result = Operations.log(4);
        assertEquals(1.386, result, 0.001);
    }

    @Test
    void testLogInvalidThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            Operations.log(0);
        });
    }

    @Test
    void testPowValid() {
        double result = Operations.pow(4, 2);
        assertEquals(16, result);
    }

    @Test
    void testPowInvalidThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            Operations.pow(0, -2);
        });
    }


}