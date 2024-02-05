package ru.rtk;

public class Main {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        CommandBuilder commandBuilder = new CommandBuilder(args);
        commandBuilder.executeCommand();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Время выполнения: " + executionTime + " миллисекунд");
    }
}
