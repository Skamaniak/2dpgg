package com.pgg.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.pgg.player.Player;

public class Keyboard {

    public boolean moveCamera(OrthographicCamera camera) {
        float zoom = stepZoom(camera.zoom);

        if (zoom != camera.zoom) {
            camera.zoom = zoom;
            return true;
        }
        return false;
    }

    public boolean movePlayer(Player player) {
        boolean moved = false;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.moveLeft();
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.moveRight();
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.moveDown();
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)  || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.moveUp();
            moved = true;
        }
        return moved;
    }

    private float stepZoom(float zoom) {
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            return zoom >= 20f ? 20f : zoom + 0.1f;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            return zoom <= 0.5f ? 0.5f : zoom - 0.1f;
        }
        return zoom;
    }
}
