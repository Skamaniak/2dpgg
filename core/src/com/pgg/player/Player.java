package com.pgg.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Player {
    private final Texture texture = new Texture("player.png"); //TODO use AssetManager?
    private static final int MAX_SPEED = 15;
    private static final int SPEED_INCREMENT = 2;
    private static final int SPEED_DECREMENT = 1;

    public float x;
    public float y;

    private int speedX = 0;
    private int speedY = 0;
    private boolean dampenX = true;
    private boolean dampenY = true;

    public void render(Batch batch) {
        batch.draw(texture, x - 16, y - 16);
    }

    public void accelerateLeft() {
        dampenX = false;
        if (speedX > -MAX_SPEED) {
            speedX -= SPEED_INCREMENT;
        }
    }

    public void accelerateRight() {
        dampenX = false;
        if (speedX < MAX_SPEED) {
            speedX += SPEED_INCREMENT;
        }
    }

    public void accelerateUp() {
        dampenY = false;
        if (speedY < MAX_SPEED) {
            speedY += SPEED_INCREMENT;
        }
    }

    public void accelerateDown() {
        dampenY = false;
        if (speedY > -MAX_SPEED) {
            speedY -= SPEED_INCREMENT;
        }
    }

    public boolean movePlayer() {
        x += speedX;
        y += speedY;
        boolean moved = speedX != 0 || speedY != 0;

        if (speedX != 0 && dampenX) {
            speedX = speedX > 0 ? speedX - SPEED_DECREMENT : speedX + SPEED_DECREMENT;
        }

        if (speedY != 0 && dampenY) {
            speedY = speedY > 0 ? speedY - SPEED_DECREMENT : speedY + SPEED_DECREMENT;
        }
        dampenX = true;
        dampenY = true;
        return moved;
    }
}
