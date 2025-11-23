package com.identify.product.FamilyKart.practise.interfacepractise;

public interface IFeaturePhone {

    void call();
    String sendMessage(String message);
    default void playGames(){}
    static void brandInfo(){}
}
