package ru.rtk;

public class Main {
    public static void main(String[] args) {

        CommandBuilder commandBuilder = new CommandBuilder(args);

        // Выполняем генерацию фрактала с одним потоком
        commandBuilder.executeCommand(1);

        // Выполняем генерацию фрактала с макс. кол-вом потоков для процессора
        int maxThreads = Runtime.getRuntime().availableProcessors();
        commandBuilder.executeCommand(maxThreads);

        System.out.println("Check result image in the same directory as jar-file");
    }
}
