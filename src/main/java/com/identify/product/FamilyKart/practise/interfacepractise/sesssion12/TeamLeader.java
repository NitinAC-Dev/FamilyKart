package com.identify.product.FamilyKart.practise.interfacepractise.sesssion12;

public abstract class TeamLeader extends Employee implements IGamesClub{
    String salary;
    public TeamLeader(long employeeID, String employeeName, String city,String salary) {
        super(employeeID, employeeName, city);
        this.salary=salary;
    }



    @Override
    public void projectDetails() {

    }

    @Override
    public void calBonus(double amount) {
        System.out.println("Bonus amount for Team Leader: " + (amount * 0.15));
    }



    @Override
    public void showPolicyDetails() {
        System.out.println("show policy details- team leader");
    }

    @Override
    public String[] outDoorGames() {
        return new String[0];
    }


}
