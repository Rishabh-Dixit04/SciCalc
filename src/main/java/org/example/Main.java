package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("Scientific Calculator Menu");
            System.out.println("1. Square Root (√x)");
            System.out.println("2. Factorial (x!)");
            System.out.println("3. Natural Logarithm (ln(x))");
            System.out.println("4. Power (x^b)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice = sc.nextInt();
            double x, b;

            switch (choice){
                case 1:
                    System.out.println("Enter number (x):");
                    x = sc.nextDouble();
                    try{
                        double result = Operations.squareRoot(x);
                        System.out.println("Output: " + result);
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Enter number (x): ");
                    x = sc.nextDouble();

                    try{
                        double result = Operations.factorial(x);
                        System.out.println("Output: " + result);
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Enter number (x): ");
                    x = sc.nextDouble();

                    try{
                        double result = Operations.log(x);
                        System.out.println("Output: " + result);
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Enter base (x): ");
                    x = sc.nextDouble();
                    System.out.println("Enter exponent (b): ");
                    b = sc.nextDouble();

                    try{
                        double result = Operations.pow(x, b);
                        System.out.println("Output: " + result);
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Thanks for visiting!");
                    return;

                default:
                    System.out.println("Invalid choice, try again!");
            }

        }
    }
}