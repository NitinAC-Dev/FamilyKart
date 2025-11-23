package com.identify.product.FamilyKart.practise.interfacepractise;

public class TouchScreenMobile extends BasicMobile implements ISmartPhone{
    @Override
    public String[] showApps() {
        return new String[]{"Youtube ","Facebook","Instagram"};
    }

    @Override
    public void useCamera() {
        System.out.println("Using camera in TouchScreen Mobile");

    }

    @Override
    public void payBills(String type) {
        System.out.println("Paying bill of type: " + type);
    }
}
