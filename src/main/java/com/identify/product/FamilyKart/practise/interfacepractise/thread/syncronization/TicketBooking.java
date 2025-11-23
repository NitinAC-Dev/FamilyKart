package com.identify.product.FamilyKart.practise.interfacepractise.thread.syncronization;

public class TicketBooking {

    double bookTicket(String passengerName, int nofofTickets) {
        System.out.println("Passenger name: " + passengerName);
        System.out.println("Number of tickets: " + nofofTickets);
        double totalAmount = nofofTickets * 450.0;
        System.out.println("Total amount: " + totalAmount);
        return totalAmount;
    }
}
