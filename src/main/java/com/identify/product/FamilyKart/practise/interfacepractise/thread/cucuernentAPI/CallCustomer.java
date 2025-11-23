package com.identify.product.FamilyKart.practise.interfacepractise.thread.cucuernentAPI;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CallCustomer {

    public static void main(String args) throws ExecutionException, InterruptedException {
        Bank bank=new Bank();
        String name="Raju";
        double amount=10000;
        Callable<Double> callableTask =()->
        {
            System.out.println("inside run");
            double interest = bank.calcInterest(name, amount);
            System.out.println(interest);
            bank.payBills("mobile",2000);
            return interest;
        };

        ExecutorService service = java.util.concurrent.Executors.newFixedThreadPool(5);
        Future<Double> doubleFuture = service.submit(callableTask);
        Double value = doubleFuture.get();

        System.out.println("Final interest value: "+value);
    }
}
