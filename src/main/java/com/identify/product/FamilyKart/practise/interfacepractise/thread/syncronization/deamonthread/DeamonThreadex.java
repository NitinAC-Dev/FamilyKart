package com.identify.product.FamilyKart.practise.interfacepractise.thread.syncronization.deamonthread;



class MyDeamonThread implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i <10 ; i++) {
            System.out.println("Deamon thread running "+i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
public class DeamonThreadex {

    public static void main(String[] args) {


        Thread t=new Thread(new MyDeamonThread(),"DemonThread");
        t.start();


        Runnable task= ()->{
            for (int i = 0; i <5 ; i++) {
                System.out.println("User thread running "+i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread userThread=new Thread(task,"userThread");
        userThread.setDaemon(true);
        userThread.start();
    }
}
