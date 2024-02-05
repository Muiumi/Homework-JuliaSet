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

    public void executeCommand() {

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
        if (image_width == 0 || image_height == 0 || (realPart * imaginaryPart == 0d) || outputFileName == null) {
            System.out.println("Неправильный ввод аргументов.");
        } else {
            JuliaFractal juliaFractal = new JuliaFractal(image_width, image_height, realPart, imaginaryPart);
            BufferedImage image = juliaFractal.generateFractal();
            try {
                File output = new File("output/" + outputFileName);
                ImageIO.write(image, "png", output);
            } catch (IOException e) {
                System.out.println("Ошибка при сохранении изображения: " + e.getMessage());
            }
        }

    }
}
