package com.identify.product.FamilyKart.practise.interfacepractise.session13;

public class CardPayment implements Ipayment {

    @Override
    public String processPayment(double amount) {
        return "Card Payment of amount: "+amount+" processed successfully.";
    }
}
