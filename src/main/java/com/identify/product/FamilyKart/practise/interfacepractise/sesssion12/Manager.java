package com.identify.product.FamilyKart.practise.interfacepractise.sesssion12;

public class Manager extends Employee implements IArtsClub{

String department;

    public Manager(long employeeID, String employeeName, String city, String department) {
        super(employeeID, employeeName, city);
        this.department = department;
    }

    @Override
    public void showActivities() {

        System.out.println(" Activities for Manager in department: " + department);
    }

    @Override
    public void projectDetails() {
        System.out.println("Project details for Manager in department: " + department);
    }

    @Override
    public void calBonus(double amount) {
        System.out.println("Bonus amount for Manager: " + (amount * 0.2));
    }

    @Override
    public void showPolicyDetails() {
        System.out.println("show policy details");
    }

    @Override
    public void showArtsActivities() {
        System.out.println(" Arts Activities for Manager in department: " + department);
    }

    @Override
    public String[] artClasses() {
        return new String[]{"Painting", "Sculpture", "Photography"};
    }


}
