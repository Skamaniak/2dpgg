package com.pgg.generation.gradient;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pgg.map.Tile;

import java.util.*;
import java.util.stream.Collectors;

public class LandscapeGradient {
    /**
     * there is <0, 100> regions in the gradient which is 101
     */
    public static final int REGIONS = 101;

    private final TextureRegion[] gradientTextures;

    public LandscapeGradient() {
        gradientTextures = GradientBuilder.builder(Color.BLACK, Color.WHITE)
                .addColor(Color.BLUE, 5)
                .addColor(new Color(0.95f, 0.95f, 0.65f, 1f), 15) // Beach sand
                .addColor(new Color(0.5f, 0.8f, 0.3f, 1f), 30) // Grass1
                .addColor(new Color(0.3f, 0.5f, 0.3f, 1f), 70) // Grass2
                .addColor(Color.GRAY, 80)
                .addColor(Color.WHITE, 95)
                .build();
    }

    /**
     * Returns gradient texture
     * @param position float number <0, 1>
     * @return texture
     */
    public TextureRegion getTexture(float position) {
        int index = (int) (position * 100);
        return gradientTextures[index];
    }

    private static List<Color> generateColorGradient(List<GradientPoint> gradientPoints) {
        List<Color> gradient = new ArrayList<>();
        GradientPoint previous = null;
        for (GradientPoint gradientPoint : gradientPoints) {
            if (previous != null) {
                int previousIndex = previous.position;
                int nextIndex = gradientPoint.position;

                float step = nextIndex - previousIndex;
                float redStep = (gradientPoint.color.r - previous.color.r) / step;
                float greenStep = (gradientPoint.color.g - previous.color.g) / step;
                float blueStep = (gradientPoint.color.b - previous.color.b) / step;
                float alphaStep = (gradientPoint.color.a - previous.color.a) / step;

                for (int i = 0; i < gradientPoint.position - previousIndex; i++) {
                    Color gradientColor = new Color(previous.color.r + (i * redStep),
                            previous.color.g + (i * greenStep),
                            previous.color.b + (i * blueStep),
                            previous.color.a + (i * alphaStep));
                    gradient.add(gradientColor);
                }
            }
            previous = gradientPoint;
        }
        gradient.add(gradientPoints.get(gradientPoints.size() - 1).color);
        return gradient;
    }

    private static TextureRegion generateGradientTextures(List<Color> colorGradient) {
        Pixmap p = new Pixmap(Tile.TILE_SIZE * REGIONS, Tile.TILE_SIZE, Pixmap.Format.RGBA8888);
        int x = 0;

        for (Color color : colorGradient) {
            p.setColor(color);
            p.fillRectangle(x, 0, Tile.TILE_SIZE, Tile.TILE_SIZE);
            x += Tile.TILE_SIZE;
        }
        Texture gradientTexture = new Texture(p);

        p.dispose();
        return new TextureRegion(gradientTexture, gradientTexture.getWidth(), gradientTexture.getHeight());
    }

    public static class GradientPoint {
        private final Color color;
        private final int position;

        public GradientPoint(Color color, int position) {
            this.color = color;
            this.position = position;
        }
    }

    public static class GradientBuilder {
        private final Map<Integer, Color> gradientPoints = new HashMap<>();

        public GradientBuilder(Color start, Color end) {
            gradientPoints.put(0, start);
            gradientPoints.put(100, end);
        }

        public static GradientBuilder builder(Color start, Color end) {
            return new GradientBuilder(start, end);
        }

        public GradientBuilder addColor(Color color, int position) {
            if (position <= 0 || position >= 100) {
                throw new InputMismatchException("Invalid position argument. Expected <1, 99> but was " + position);
            }
            if (gradientPoints.containsKey(position)) {
                throw new InputMismatchException("Invalid position argument. Position " + position + " already defined");
            }

            this.gradientPoints.put(position, color);
            return this;
        }

        public TextureRegion[] build() {
            List<GradientPoint> points = gradientPoints.entrySet().stream()
                    .sorted(Comparator.comparingInt(Map.Entry::getKey))
                    .map(e -> new GradientPoint(e.getValue(), e.getKey()))
                    .collect(Collectors.toList());

            List<Color> colors = generateColorGradient(points);
            TextureRegion region = generateGradientTextures(colors);
            return region.split(Tile.TILE_SIZE, Tile.TILE_SIZE)[0];
        }
    }

}
