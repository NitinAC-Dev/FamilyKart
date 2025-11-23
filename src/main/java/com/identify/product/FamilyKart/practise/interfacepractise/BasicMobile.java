package com.identify.product.FamilyKart.practise.interfacepractise;

public class BasicMobile implements IFeaturePhone{
    @Override
    public void call() {
        System.out.println("Calling from Basic Mobile");
    }

    @Override
    public String sendMessage(String message) {
        return "sending message: " + message;
    }
}
