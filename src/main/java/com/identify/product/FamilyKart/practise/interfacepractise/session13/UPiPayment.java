package com.identify.product.FamilyKart.practise.interfacepractise.session13;

public class UPiPayment implements Ipayment {

    @Override
    public String processPayment(double amount) {
        return "UPI Payment of amount: "+amount+" processed successfully.";
    }
}
