package ru.rtk;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class JuliaFractal {

    private final int width;
    private final int height;
    private final double constantRealPart;
    private final double constantImaginaryPart;
    private final int chunks;

    private final int ITERATIONS = 1000;

    public JuliaFractal(int width, int height, double constantRealPart, double constantImaginaryPart, int chunks) {
        this.width = width;
        this.height = height;
        this.constantRealPart = constantRealPart;
        this.constantImaginaryPart = constantImaginaryPart;
        this.chunks = chunks;
    }

    public BufferedImage generateFractal() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        try (ExecutorService executorService = Executors.newFixedThreadPool(chunks)) {
            List<Future<List<Pixel>>> futures = new ArrayList<>();

            for (int i = 0; i < chunks; i++) {
                int chunkYStart = i * (height / chunks);
                int chunkYEnd = (i + 1) * (height / chunks);

                Callable<List<Pixel>> chunk = new JuliaFractalChunk(chunkYStart, chunkYEnd);
                Future<List<Pixel>> future = executorService.submit(chunk);
                futures.add(future);
            }
            for (Future<List<Pixel>> future : futures) {
                try {
                    List<Pixel> pixels = future.get();
                    for (Pixel pixel : pixels) {
                        image.setRGB(pixel.x(), pixel.y(), pixel.color().getRGB());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Exception while building fractal " + e.getMessage());
                }
            }
        }

        return image;
    }

    private class JuliaFractalChunk implements Callable<List<Pixel>> {

        private final int chunkYStart;
        private final int chunkYEnd;

        private final float COLOR_SATURATION = 1f;
        private final float COLOR_BRIGHTNESS = 1f;

        public JuliaFractalChunk(int chunkYStart, int chunkYEnd) {
            this.chunkYStart = chunkYStart;
            this.chunkYEnd = chunkYEnd;
        }

        @Override
        public List<Pixel> call() {

            List<Pixel> pixels = new ArrayList<>();

            for (int x = 0; x < width; x++) {
                for (int y = chunkYStart; y < chunkYEnd; y++) {

                    double RealPart = 2.0 * (x - width * 0.5) / (width * 0.5);
                    double ImaginaryPart = 1.33 * (y - height * 0.5) / (height * 0.5);

                    int i;
                    for (i = 0; i < ITERATIONS; i++) {
                        double temp = RealPart * RealPart - ImaginaryPart * ImaginaryPart + constantRealPart;
                        ImaginaryPart = 2.0 * RealPart * ImaginaryPart + constantImaginaryPart;
                        RealPart = temp;

                        // Условие для проверки входит ли текущая точка при текущем кол-ве итераций в множество Жюлиа
                        if (RealPart * RealPart + ImaginaryPart * ImaginaryPart >= 4) {
                            break;
                        }
                    }

                    // Вычисляем значение тона цвета в зависимости от количества пройденных итерация
                    float hue = (i % 256) / 255.0f;

                    Color color = Color.getHSBColor(hue, COLOR_SATURATION, COLOR_BRIGHTNESS);
                    pixels.add(new Pixel(x, y, color));
                }
            }
            return pixels;
        }
    }
}


