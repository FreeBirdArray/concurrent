package com.ghost.concurrent.chapter08;


public class ClinitMethodTest {

    private final static String STR = "clinlt() 测试";
    private final static boolean RESULT = true ;
    private static String x = "sss";

    public static void main(String[] args) {
        ClinitMethodTest clinitMethodTest = new ClinitMethodTest();
        System.out.println(ClinitMethodTest.RESULT);

    }
}
