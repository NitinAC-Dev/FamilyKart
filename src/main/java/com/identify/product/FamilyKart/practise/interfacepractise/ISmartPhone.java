package com.identify.product.FamilyKart.practise.interfacepractise;

public interface ISmartPhone extends IFeaturePhone {
    String[] showApps();
    void  useCamera();
    void payBills(String type);
    default void downloadMusic(){}
    static void brandInfo(){}
}
