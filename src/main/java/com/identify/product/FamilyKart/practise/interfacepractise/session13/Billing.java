package com.identify.product.FamilyKart.practise.interfacepractise.session13;

public class Billing {

    public static void main(String[] args) {
        Orders order = new Orders();
        order.process(99.99, 3);


        Ipayment payment =amount->{
            if (amount<=5000)
            {
                return "Wallet Payment of amount: "+amount+" processed successfully.";
            }
            else
            {
                return "Wallet Payment failed. Amount exceeds limit.";
            }
        };

        String processPayment = payment.processPayment(5500);
        System.out.println(processPayment);
    }
}
