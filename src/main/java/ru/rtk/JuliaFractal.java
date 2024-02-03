package ru.rtk;

import java.awt.*;
import java.awt.image.BufferedImage;

public class JuliaFractal {
    private final int width;
    private final int height;
    private final double realPart;
    private final double imaginaryPart;
    private final int ITERATIONS = 2048;
    private final float saturation = 1f;

    public JuliaFractal(int width, int height, double realPart, double imaginaryPart) {
        this.width = width;
        this.height = height;
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public BufferedImage generateFractal() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double z0RePart = 2.0 * (x - width * 0.5) / (width * 0.5);
                double z0ImPart = 1.33 * (y - height * 0.5) / (height * 0.5);

                int i;
                for (i = 0; i < ITERATIONS; i++) {
                    double xTemp = z0RePart * z0RePart - z0ImPart * z0ImPart + realPart;
                    z0ImPart = 2.0 * z0RePart * z0ImPart + imaginaryPart;
                    z0RePart = xTemp;

                    if (z0RePart * z0RePart + z0ImPart * z0ImPart >= 4) {
                        break;
                    }
                }

                float brightness = i < ITERATIONS ? 1f : 0;
                float hue = (i % 256) / 255.0f;

                Color color = Color.getHSBColor(hue, saturation, brightness);
                image.setRGB(x, y, color.getRGB());
            }
        }

        return image;
    }
}


