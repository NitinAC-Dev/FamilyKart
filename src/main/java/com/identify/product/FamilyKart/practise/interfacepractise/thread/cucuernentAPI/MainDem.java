package com.identify.product.FamilyKart.practise.interfacepractise.thread.cucuernentAPI;

 class test {
     static void process()  {
        System.out.print("Hello");
       }}
class B extends test{
 static void process() {
         System.out.print("Welcome");
        }}
public class MainDem{
    public static void main(String[] args) {
        test obj=new B();
        obj.process();
        //this is the change
    }
}

