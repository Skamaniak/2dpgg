package com.pgg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.pgg.generation.TileGenerator;
import com.pgg.generation.landscape.Biome;
import com.pgg.input.Keyboard;
import com.pgg.map.MapChunk;
import com.pgg.map.MapChunkLoader;
import com.pgg.map.tile.TerrainFeatureTile;
import com.pgg.player.Player;
import com.pgg.view.GameRenderer;

import java.util.Random;

public class Main extends ApplicationAdapter {

    private static final int SEED = 12;
    private static final int LOAD_SURROUNDINGS = 4;
    private static final int LOAD_DISTANCE = 8;
    private static final int VIEWPORT_WIDTH = 320;
    private static final int VIEWPORT_HEIGHT = 180;

    private OrthographicCamera camera;
    private GameRenderer renderer;
    private Keyboard keyboard;
    private TileGenerator generator;
    private MapChunkLoader mapChunkLoader;
    private Player player;

    @Override
    public void create() {
        Random masterRandom = new Random(SEED);

        player = new Player();

        keyboard = new Keyboard();
        camera = initiateCamera();

        generator = new TileGenerator(masterRandom);
        mapChunkLoader = new MapChunkLoader(generator, LOAD_SURROUNDINGS, LOAD_DISTANCE);
        renderer = new GameRenderer(camera, player);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        keyboard.readInputs(player);
        MapChunk playerChunk = mapChunkLoader.getChunkAt(player.roundX, player.roundY);
        float speedModifier = 1f;
        if (playerChunk != null) {
            speedModifier = playerChunk.getLandscapeAt(player.roundX, player.roundY).getSpeedModifier();
            TerrainFeatureTile terrainFeatureTile = playerChunk.getTerrainFeatureAt((int)player.x, (int)player.y);
            if (terrainFeatureTile != null) {
                speedModifier *= terrainFeatureTile.getSpeedModifier();
            }
        }
        boolean updateCamera = player.movePlayer(speedModifier);
        updateCamera |= keyboard.moveCamera(camera);
        if (updateCamera) {
            updateCamera();
        }
        renderer.render(mapChunkLoader.getChunks(player.roundX, player.roundY));
    }

    private void updateCamera() {
        camera.position.x = player.x;
        camera.position.y = player.y;
        camera.update();
    }

    private OrthographicCamera initiateCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.zoom = 4f;
        camera.update();
        return camera;
    }

    @Override
    public void dispose() {
        renderer.dispose();
        player.dispose();
    }
}
