package com.identify.product.FamilyKart.practise.interfacepractise.thread.cucuernentAPI;

public class Bank {
    public synchronized double calcInterest(String name, double amount) {
        System.out.println("Interest cal for "+name);
        double interest = (amount*3*5)/100;
        System.out.println("Interest "+interest);
        return interest;
    }

    public void payBills(String type, double amount) {
        System.out.println("Bill payment for "+type);
        System.out.println("Paid "+amount);

    }
}
