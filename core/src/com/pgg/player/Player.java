package com.pgg.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Player {
    private final Texture texture = new Texture("player.png"); //TODO use AssetManager?
    private static final int SPEED = 25;

    public float x;
    public float y;

    private float speed = SPEED;

    public void render(Batch batch) {
        batch.draw(texture, x - 16, y - 16);
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void moveUp() {
        y += speed;
    }

    public void moveDown() {
        y -= speed;
    }
}
