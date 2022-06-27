package com.yimaag.log;


public class LogGenerator {
    public static String enter(String message){
        StringBuilder builder = new StringBuilder();

        builder.append("ENTER|");
        builder.append(getCurrentMethodName());
        builder.append("|" + message);

        return builder.toString();
    }

    public static String enter(){
        StringBuilder builder = new StringBuilder();

        builder.append("ENTER|");
        builder.append(getCurrentMethodName());

        return builder.toString();
    }

    public static String check(String message){
        StringBuilder builder = new StringBuilder();

        builder.append("CHECK|");
        builder.append(getCurrentMethodName());
        builder.append("|" + message);

        return builder.toString();
    }

    public static String critical(String message){
        StringBuilder builder = new StringBuilder();

        builder.append("CRITICAL|");
        builder.append(getCurrentMethodName());
        builder.append("|" + message);

        return builder.toString();
    }

    public static String response(String message){
        StringBuilder builder = new StringBuilder();

        builder.append("RESPONSE|");
        builder.append(getCurrentMethodName());
        builder.append("|" + message);

        return builder.toString();
    }

    public static String exception(Exception e){
        StringBuilder builder = new StringBuilder();

        builder.append("EXCEPTION|");

        builder.append(getCurrentMethodName());

        builder.append("|" + e.getClass().getSimpleName());
        builder.append("| message: " + e.getMessage());

        return builder.toString();
    }

    public static String error(String message){
        StringBuilder builder = new StringBuilder();

        builder.append("ERROR|");
        builder.append(getCurrentMethodName());
        builder.append("|" + message);

        return builder.toString();
    }

    public static String getCurrentMethodName(){
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();

        return elements[3].getMethodName();
    }

    public static void methodStackTrace(){
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for(StackTraceElement element : elements){
            System.out.println("ClassName: " + element.getClassName());
            System.out.println("MethodName: " + element.getMethodName());
        }

        System.out.println("This class name: " + LogGenerator.class.getName());
    }
}
