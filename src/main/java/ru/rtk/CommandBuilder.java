package ru.rtk;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CommandBuilder {

    private final String[] args;

    public CommandBuilder(String[] args) {
        this.args = args;
    }

    public void executeCommand(int threadNumber) {

        if (args.length < 6) {
            throw new RuntimeException("Incorrect arguments. Example: java -jar juliasetgen.jar -d 4096;4096 -c -0.75;0.11 -o 123.png");
        }

        System.out.println("Generating fractal with threads number: " + threadNumber);
        long startTime = System.currentTimeMillis();

        int image_width = 0;
        int image_height = 0;
        double realPart = 0d;
        double imaginaryPart = 0d;
        String outputFileName = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-d" -> {
                    String[] dimensions = args[i + 1].split(";");
                    image_width = Integer.parseInt(dimensions[0]);
                    image_height = Integer.parseInt(dimensions[1]);
                }
                case "-c" -> {
                    String[] integrationConstant = args[i + 1].split(";");
                    realPart = Double.parseDouble(integrationConstant[0]);
                    imaginaryPart = Double.parseDouble(integrationConstant[1]);
                }
                case "-o" -> outputFileName = args[i + 1];
            }
        }
        if (image_width == 0 || image_height == 0 || (Math.abs(realPart) + Math.abs(imaginaryPart) == 0d) || outputFileName == null) {
            throw new RuntimeException("Incorrect arguments. Example: java -jar juliasetgen.jar -d 4096;4096 -c -0.75;0.11 -o 123.png");
        } else {
            JuliaFractal juliaFractal = new JuliaFractal(image_width, image_height, realPart, imaginaryPart, threadNumber);
            BufferedImage image = juliaFractal.generateFractal();
            try {
                File output = new File(outputFileName);
                ImageIO.write(image, "png", output);
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                System.out.println("Complete with: " + executionTime + " milliseconds");
            } catch (IOException e) {
                System.out.println("Exception while saving image: " + e.getMessage());
            }
        }

    }
}
