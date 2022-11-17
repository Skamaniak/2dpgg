package com.pgg.generation.landscape;

import com.badlogic.gdx.graphics.Color;

public enum Biome {
    DEEP_WATER(Color.BLACK, 0.1f),
    WATER(Color.BLUE, 0.3f),
    BEACH(new Color(0.95f, 0.95f, 0.65f, 1f), 1f),
    MEADOW(new Color(0.5f, 0.8f, 0.3f, 1f), 1.2f),
    GRASS(new Color(0.3f, 0.5f, 0.3f, 1f), 1.2f),
    HILLS(Color.GRAY, 0.8f),
    HIGH_HILLS(Color.LIGHT_GRAY, 0.6f),
    SNOW(Color.WHITE, 0.4f);


    public final Color color;
    public final float speedModifier;

    Biome(Color color, float speedModifier) {
        this.color = color;
        this.speedModifier = speedModifier;
    }
}
