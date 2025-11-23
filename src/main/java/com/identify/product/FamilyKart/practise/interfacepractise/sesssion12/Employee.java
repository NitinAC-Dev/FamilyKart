package com.identify.product.FamilyKart.practise.interfacepractise.sesssion12;

public abstract class Employee implements Insurance , ActivityClub {

    private long employeeID;

    private String employeeName;

    private String city;

    public Employee(long employeeID, String employeeName, String city) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.city = city;
    }

    public void printDetails()
    {
        System.out.println("Employee ID: " + employeeID);
        System.out.println("Employee Name: " + employeeName);
        System.out.println("City: " + city);
    }

    public abstract void projectDetails();

    public abstract void calBonus(double amount);


    abstract String[] artClasses();
}
