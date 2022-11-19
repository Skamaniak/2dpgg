package com.pgg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.pgg.generation.TileGenerator;
import com.pgg.input.Controls;
import com.pgg.input.Keyboard;
import com.pgg.inventory.HotBar;
import com.pgg.inventory.item.Tool;
import com.pgg.map.MapChunk;
import com.pgg.map.MapChunkLoader;
import com.pgg.map.tile.LandscapeTile;
import com.pgg.map.tile.TerrainFeatureTile;
import com.pgg.map.tile.Tile;
import com.pgg.player.Player;
import com.pgg.view.SceneCamera;
import com.pgg.view.SceneRenderer;
import com.pgg.view.UiCamera;
import com.pgg.view.UiRenderer;

import java.util.Random;

public class Main extends ApplicationAdapter {

    private static final int SEED = 12;
    private static final int LOAD_SURROUNDINGS = 4;
    private static final int LOAD_DISTANCE = 8;

    private SceneCamera sceneCamera;
    private UiCamera uiCamera;
    private SceneRenderer sceneRenderer;
    private UiRenderer uiRenderer;
    private Keyboard keyboard;
    private TileGenerator generator;
    private MapChunkLoader mapChunkLoader;
    private Player player;
    private HotBar hotBar;

    @Override
    public void create() {
        Random masterRandom = new Random(SEED);

        keyboard = new Keyboard();
        Debug.registerInputActions(keyboard);

        // CAMERAS
        uiCamera = new UiCamera();
        sceneCamera = new SceneCamera();
        sceneCamera.registerInputActions(keyboard);

        // CHUNK LOADING
        generator = new TileGenerator(masterRandom);
        mapChunkLoader = new MapChunkLoader(generator, LOAD_SURROUNDINGS, LOAD_DISTANCE);

        // UI
        hotBar = new HotBar(uiCamera);
        hotBar.registerInputActions(keyboard);
        uiRenderer = new UiRenderer(uiCamera, hotBar);

        // SCENE
        player = new Player();
        player.registerInputActions(keyboard);
        sceneRenderer = new SceneRenderer(sceneCamera, player);

        registerDebug();

        // FIXME remove, used for testing only
        hotBar.setItem(0, Tool.AXE);
        hotBar.setItem(1, Tool.PICKAXE);
    }

    private void registerDebug() {
        keyboard.registerKeyJustPressAction(Controls.DEBUG_PRINT_INFO, () -> {
            if (Debug.DEBUG_ENABLED) {
                System.out.println();
                System.out.println("Player coordinates: " + player.x + ", " + player.y);
                MapChunk currentChunk = mapChunkLoader.getChunkAt(player.roundX, player.roundY);
                System.out.println("Current chunk: " + currentChunk.chunkX + ", " + currentChunk.chunkY);
                LandscapeTile landscapeTile = currentChunk.getLandscapeAt(player.roundX, player.roundY);
                int playerTileX = player.roundX / Tile.TILE_SIZE;
                int playerTileY = player.roundY / Tile.TILE_SIZE;
                System.out.println("Player tile: " + playerTileX + ", " + playerTileY);
                System.out.println("Landscape tile: " + landscapeTile.biome + ", speed modifier: " + landscapeTile.getSpeedModifier());
                TerrainFeatureTile terrainFeatureTile = currentChunk.getTerrainFeatureAt(player.roundX, player.roundY);
                if (terrainFeatureTile == null) {
                    System.out.println("Terrain feature tile: none");
                } else {
                    System.out.println("Terrain feature tile: speed modifier: " + terrainFeatureTile.getSpeedModifier());
                }
            }
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        keyboard.readInputs();

        MapChunk playerChunk = mapChunkLoader.getChunkAt(player.roundX, player.roundY);
        float speedModifier = 1f;
        if (playerChunk != null) {
            speedModifier = playerChunk.getLandscapeAt(player.roundX, player.roundY).getSpeedModifier();
            TerrainFeatureTile terrainFeatureTile = playerChunk.getTerrainFeatureAt((int) player.x, (int) player.y);
            if (terrainFeatureTile != null) {
                speedModifier *= terrainFeatureTile.getSpeedModifier();
            }
        }
        player.movePlayer(speedModifier);
        sceneCamera.updatePosition(player.x, player.y);
        sceneRenderer.render(mapChunkLoader.getChunks(player.roundX, player.roundY));
        uiRenderer.render();
    }

    @Override
    public void dispose() {
        sceneRenderer.dispose();
        uiRenderer.dispose();
        player.dispose();
        hotBar.dispose();
    }
}
