package com.identify.product.FamilyKart.practise.interfacepractise.thread.cucuernentAPI;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompleteableFutureDemo {
    int [] nums = null;
    public static String showMessage(Supplier<String> supplier)
    {
        int [] nums = null;
        nums[0]=100;
        return supplier.get();
    }

    public static String getValue(Consumer<String> consumer, String value)
    {
        consumer.accept(value);
        return "done";
    }

    public static String getMessageLength(Function<String,Integer> func,String Message)
    {
        Integer apply = func.apply(Message);
        return "Length is: "+apply;
    }

    public static void main(String[] args) {
        Supplier<String> supplier =()->  "Hello World";
        System.out.println(supplier.get());

        String value = showMessage(() -> "this is my supplier message");

        Consumer<String> consumer= (myValue) -> System.out.println("Consumer value: "+myValue.toUpperCase());
consumer.accept("value for consumer");

        String result = getValue((strValue)-> System.out.println(strValue.toUpperCase()), "another value for consumer");
        System.out.println("Result from getValue: "+result);
        String helloFunction = getMessageLength((str) -> str.length(), "Hello Function");
        System.out.println(helloFunction);

    }
}
