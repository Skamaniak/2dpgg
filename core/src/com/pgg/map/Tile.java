package com.pgg.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum Tile {
    DEEP_WATER(0, 0),
    WATER(1, 0),
    SHALLOW_WATER(2, 0),
    BEACH(3, 0),
    MARSHLANDS(4, 0),
    MEADOW(5, 0),
    GRASS(6, 0),
    ROCKS(7, 0),
    MOUNTAIN(8, 0),
    SNOW(9, 0);

    public static final int TILE_SIZE = 32;
    private static final TextureRegion[][] TILES = new TextureRegion(new Texture("tileset.png"))
            .split(TILE_SIZE, TILE_SIZE);

    private final int x;
    private final int y;

    Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public TextureRegion getTile() {
        return TILES[y][x];
    }
}
