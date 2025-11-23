package com.identify.product.FamilyKart.practise.interfacepractise.thread.syncronization;

public class Passenger implements Runnable{


    String passengerName;
int nofofTickets;

final TicketBooking ticketBooking;
    public Passenger(String passengerName, int nofofTickets, TicketBooking ticketBooking) {
        super();
        this.passengerName = passengerName;
        this.nofofTickets = nofofTickets;
        this.ticketBooking=ticketBooking;
        Thread t=new Thread(this,passengerName);
        t.start();
    }

    @Override
    public void run() {
        System.out.println("Welcome to the ticket booking counter");
        System.out.println("-----------------------------------");
        System.out.println("Passenger name: "+passengerName);

        synchronized (ticketBooking) {
            double amount = ticketBooking.bookTicket(passengerName, nofofTickets);
            System.out.println("Please pay the amount: " + amount);
            System.out.println("-----------------------------------");
        }

        System.out.println("Thank you for booking tickets with us, " + passengerName);
        System.out.println("-----------------------------------");


    }
}
