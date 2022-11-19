package com.pgg.map.tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TessellatedTileSet<T extends Tile> {
    public static final int TOP_NEIGHBOUR = 1 << 3;
    public static final int LEFT_NEIGHBOUR = 1 << 2;
    public static final int RIGHT_NEIGHBOUR = 1 << 1;
    public static final int BOTTOM_NEIGHBOUR = 1;
    public static final int NO_NEIGHBOUR = 0;

    private final Map<Integer, T> tiles;
    private final Function<TextureRegion, T> tileCreator;

    public TessellatedTileSet(String tilesetPath, Function<TextureRegion, T> tileCreator) {
        this.tileCreator = tileCreator;

        TextureRegion tilesetTexture = new TextureRegion(new Texture(tilesetPath));
        TextureRegion[][] tileTextures = tilesetTexture.split(Tile.TILE_SIZE, Tile.TILE_SIZE);
        tiles = loadTiles(tileTextures);
    }

    public T getTile(int surrounding) {
        return tiles.get(surrounding);
    }

    public Map<Integer, T> loadTiles(TextureRegion[][] tileTextures) {
        Map<Integer, T> tiles = new HashMap<>();

        tiles.put(NO_NEIGHBOUR, tileCreator.apply(tileTextures[0][0]));
        tiles.put(RIGHT_NEIGHBOUR, tileCreator.apply(tileTextures[0][1]));
        tiles.put(LEFT_NEIGHBOUR + RIGHT_NEIGHBOUR, tileCreator.apply(tileTextures[0][2]));
        tiles.put(LEFT_NEIGHBOUR, tileCreator.apply(tileTextures[0][3]));
        tiles.put(BOTTOM_NEIGHBOUR, tileCreator.apply(tileTextures[0][4]));
        tiles.put(RIGHT_NEIGHBOUR + BOTTOM_NEIGHBOUR, tileCreator.apply(tileTextures[0][5]));
        tiles.put(LEFT_NEIGHBOUR + RIGHT_NEIGHBOUR + BOTTOM_NEIGHBOUR, tileCreator.apply(tileTextures[0][6]));
        tiles.put(LEFT_NEIGHBOUR + BOTTOM_NEIGHBOUR, tileCreator.apply(tileTextures[0][7]));

        tiles.put(TOP_NEIGHBOUR + BOTTOM_NEIGHBOUR, tileCreator.apply(tileTextures[1][4]));
        tiles.put(TOP_NEIGHBOUR + RIGHT_NEIGHBOUR + BOTTOM_NEIGHBOUR, tileCreator.apply(tileTextures[1][5]));
        tiles.put(TOP_NEIGHBOUR + LEFT_NEIGHBOUR + RIGHT_NEIGHBOUR + BOTTOM_NEIGHBOUR, tileCreator.apply(tileTextures[1][6]));
        tiles.put(TOP_NEIGHBOUR + LEFT_NEIGHBOUR + BOTTOM_NEIGHBOUR, tileCreator.apply(tileTextures[1][7]));

        tiles.put(TOP_NEIGHBOUR, tileCreator.apply(tileTextures[2][4]));
        tiles.put(TOP_NEIGHBOUR + RIGHT_NEIGHBOUR, tileCreator.apply(tileTextures[2][5]));
        tiles.put(TOP_NEIGHBOUR + LEFT_NEIGHBOUR + RIGHT_NEIGHBOUR, tileCreator.apply(tileTextures[2][6]));
        tiles.put(TOP_NEIGHBOUR + LEFT_NEIGHBOUR, tileCreator.apply(tileTextures[2][7]));

        return tiles;
    }

}
