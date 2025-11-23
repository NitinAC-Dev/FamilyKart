package com.identify.product.FamilyKart.practise.interfacepractise.thread.cucuernentAPI;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Runner implements Runnable{
    private Bank bank;
    private String name;
    private double amount;
    public Runner( String name, double amount,Bank bank) {
        super();
        this.bank = bank;
        this.name = name;
        this.amount = amount;
    }
    @Override
    public void run() {
        System.out.println("inside run");
        double interest = bank.calcInterest(name, amount);
        System.out.println(interest);
        bank.payBills("mobile",2000);
    }
}

public class Customer {



    public static void main(String[] args) {
        Bank bank =  new Bank();
        //create a thread pool
        ExecutorService service = Executors.newFixedThreadPool(5);
        //pass the task inside execute method
        service.execute(new Runner("Raju",10000,bank));


String name="Raju";
double amount=10000;
        service.execute(()->{
            System.out.println("inside run");
            double interest = bank.calcInterest(name, amount);
            System.out.println(interest);
            bank.payBills("mobile",2000);
        });





    }
}
