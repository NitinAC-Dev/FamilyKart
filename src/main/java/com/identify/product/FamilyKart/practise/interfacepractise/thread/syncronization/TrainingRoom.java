package com.identify.product.FamilyKart.practise.interfacepractise.thread.syncronization;

public class TrainingRoom implements Runnable{

    public TrainingRoom(String studentName, String doubt) {
        this.studentName = studentName;
        this.doubt = doubt;
    }

    String studentName;
    String doubt;
    @Override
    public void run() {

        Trainer trainer=new Trainer();
        String s = trainer.askDoubs(studentName, doubt);
    }
}
