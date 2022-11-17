package com.pgg.generation.landscape;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pgg.map.tile.LandscapeTile;
import com.pgg.map.tile.Tile;

import java.util.*;
import java.util.stream.Collectors;

public class LandscapeGradient {
    /**
     * there is <0, 100> regions in the gradient which is 101
     */
    public static final int REGIONS = 101;

    private final List<LandscapeTile> landscapeTiles;

    public LandscapeGradient() {
        landscapeTiles = GradientBuilder.builder(Biome.DEEP_WATER, Biome.SNOW)
                .addBiome(Biome.WATER, 10)
                .addBiome(Biome.BEACH, 15)
                .addBiome(Biome.MEADOW, 30)
                .addBiome(Biome.GRASS, 75)
                .addBiome(Biome.HILLS, 80)
                .addBiome(Biome.HIGH_HILLS, 92)
                .addBiome(Biome.SNOW, 95)
                .build();
    }

    /**
     * Returns gradient texture
     *
     * @param elevation float number <0, 1>
     * @return texture
     */
    public LandscapeTile getLandscapeTile(float elevation) {
        int index = (int) (elevation * 100);
        return landscapeTiles.get(index);
    }

    private static List<GradientPoint> generateGradient(List<GradientChange> gradientChanges) {
        List<GradientPoint> gradient = new ArrayList<>();
        GradientChange previous = null;

        for (GradientChange gradientChange : gradientChanges) {
            if (previous != null) {
                gradient.addAll(generateGradient(previous, gradientChange));
            }
            previous = gradientChange;
        }
        GradientChange lastGradientChange = gradientChanges.get(gradientChanges.size() - 1);
        gradient.add(new GradientPoint(lastGradientChange.biome, lastGradientChange.biome.color));
        return gradient;
    }

    private static List<GradientPoint> generateGradient(GradientChange previousChange, GradientChange nextChange) {
        int previousElevation = previousChange.elevation;
        int nextElevation = nextChange.elevation;

        Biome previousBiome = previousChange.biome;
        Biome nextBiome = nextChange.biome;

        float step = nextElevation - previousElevation;
        float redStep = (nextBiome.color.r - previousBiome.color.r) / step;
        float greenStep = (nextBiome.color.g - previousBiome.color.g) / step;
        float blueStep = (nextBiome.color.b - previousBiome.color.b) / step;
        float alphaStep = (nextBiome.color.a - previousBiome.color.a) / step;

        List<GradientPoint> gradientFragment = new ArrayList<>();
        int steps = nextElevation - previousElevation;
        for (int i = 0; i < steps; i++) {
            Color gradientColor = new Color(previousChange.biome.color.r + (i * redStep),
                    previousBiome.color.g + (i * greenStep),
                    previousBiome.color.b + (i * blueStep),
                    previousBiome.color.a + (i * alphaStep));

            Biome biome = i <= (steps / 2) ? previousBiome : nextBiome;
            gradientFragment.add(new GradientPoint(biome, gradientColor));
        }
        return gradientFragment;
    }

    private static TextureRegion generateGradientTextures(List<GradientPoint> gradient) {
        Pixmap p = new Pixmap(Tile.TILE_SIZE * REGIONS, Tile.TILE_SIZE, Pixmap.Format.RGBA8888);
        int x = 0;

        for (GradientPoint point : gradient) {
            p.setColor(point.color);
            p.fillRectangle(x, 0, Tile.TILE_SIZE, Tile.TILE_SIZE);
            x += Tile.TILE_SIZE;
        }
        Texture gradientTexture = new Texture(p);

        p.dispose();
        return new TextureRegion(gradientTexture, gradientTexture.getWidth(), gradientTexture.getHeight());
    }

    public static class GradientChange {
        private final Biome biome;
        private final int elevation;

        public GradientChange(Biome biome, int elevation) {
            this.biome = biome;
            this.elevation = elevation;
        }
    }

    private static class GradientPoint {
        private final Biome biome;
        private final Color color;

        public GradientPoint(Biome biome, Color color) {
            this.biome = biome;
            this.color = color;
        }
    }

    public static class GradientBuilder {
        private final Map<Integer, Biome> gradientChanges = new HashMap<>();

        public GradientBuilder(Biome start, Biome end) {
            gradientChanges.put(0, start);
            gradientChanges.put(100, end);
        }

        public static GradientBuilder builder(Biome start, Biome end) {
            return new GradientBuilder(start, end);
        }

        public GradientBuilder addBiome(Biome biome, int elevation) {
            if (elevation <= 0 || elevation >= 100) {
                throw new InputMismatchException("Invalid elevation argument. Expected <1, 99> but was " + elevation);
            }
            if (gradientChanges.containsKey(elevation)) {
                throw new InputMismatchException("Invalid elevation argument. Position " + elevation + " already defined");
            }

            this.gradientChanges.put(elevation, biome);
            return this;
        }

        public List<LandscapeTile> build() {
            List<GradientChange> changes = gradientChanges.entrySet().stream()
                    .sorted(Comparator.comparingInt(Map.Entry::getKey))
                    .map(e -> new GradientChange(e.getValue(), e.getKey()))
                    .collect(Collectors.toList());

            List<GradientPoint> gradient = generateGradient(changes);
            TextureRegion region = generateGradientTextures(gradient);

            TextureRegion[] textures = region.split(Tile.TILE_SIZE, Tile.TILE_SIZE)[0];

            List<LandscapeTile> tiles = new ArrayList<>();

            for (int i = 0; i < gradient.size(); i++) {
                tiles.add(new LandscapeTile(gradient.get(i).biome, i, textures[i]));
            }
            return tiles;
        }
    }



}
