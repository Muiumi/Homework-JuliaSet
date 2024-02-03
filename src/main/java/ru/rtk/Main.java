package ru.rtk;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        int width = 1920;
        int height = 1080;
        double realPart = -0.70176;
        double imaginaryPart = -0.3842;

            // Создание изображения фрактала Жулиа
            JuliaFractal juliaFractal = new JuliaFractal(width, height, realPart, imaginaryPart);
            BufferedImage image = juliaFractal.generateFractal();

            // Сохранение изображения в файл
            try {
                File output = new File("output/fractal.png");
                ImageIO.write(image, "png", output);
            } catch (IOException e) {
                System.out.println("Ошибка при сохранении изображения: " + e.getMessage());
            }
        }
    }
