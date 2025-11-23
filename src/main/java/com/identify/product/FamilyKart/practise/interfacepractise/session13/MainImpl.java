package com.identify.product.FamilyKart.practise.interfacepractise.session13;

public class MainImpl {


    public static void main(String[] args) {
        // Lambda expression to implement the calculate method
        Lambdaexpression addition = (x, y) -> System.out.println("Addition: " + (x + y));
        Lambdaexpression subtraction = (x, y) -> System.out.println("Subtraction: " + (x - y));
        Lambdaexpression multiplication = (x, y) -> System.out.println("Multiplication: " + (x * y));
        Lambdaexpression division = (x, y) -> {
            if (y != 0) {
                System.out.println("Division: " + (x / y));
            } else {
                System.out.println("Division by zero is not allowed.");
            }
        };

        // Using the lambda expressions
        addition.calculate(10, 5);
        subtraction.calculate(10, 5);
        multiplication.calculate(10, 5);
        division.calculate(10, 5);
        division.calculate(10, 0); // Testing division by zero
    }
}
