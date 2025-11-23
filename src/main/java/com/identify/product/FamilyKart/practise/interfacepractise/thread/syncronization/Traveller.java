package com.identify.product.FamilyKart.practise.interfacepractise.thread.syncronization;

public class Traveller {

    public static void main(String[] args) {
        TicketBooking ticketBooking=new TicketBooking();
        Passenger p1=new Passenger("Alice",2,ticketBooking);
        Passenger p2=new Passenger("Bob",3,ticketBooking);


    }
}
