package com.pgg.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.pgg.Debug;
import com.pgg.input.Controls;
import com.pgg.input.Keyboard;

public class Player {
    private static final int MAX_SPEED = 10;
    private static final int SPEED_INCREMENT = 2;
    private static final int SPEED_DECREMENT = 1;
    private final Texture playerTexture;
    private final Sprite playerSprite;

    public float x;
    public float y;

    public int roundX;
    public int roundY;

    private int speedX = 0;
    private int speedY = 0;

    private boolean dampenX = true;
    private boolean dampenY = true;

    public Player() {
        playerTexture = new Texture("player.png"); //TODO use AssetManager?
        playerSprite = new Sprite(playerTexture);
    }

    public void render(Batch batch) {
        playerSprite.setCenter(0, 0);
        playerSprite.setX(x - 16);
        playerSprite.setY(y - 16);
        playerSprite.draw(batch);
    }

    private void accelerateLeft() {
        dampenX = false;
        if (speedX > -MAX_SPEED) {
            speedX -= SPEED_INCREMENT;
        }
    }

    private void accelerateRight() {
        dampenX = false;
        if (speedX < MAX_SPEED) {
            speedX += SPEED_INCREMENT;
        }
    }

    private void accelerateUp() {
        dampenY = false;
        if (speedY < MAX_SPEED) {
            speedY += SPEED_INCREMENT;
        }
    }

    private void accelerateDown() {
        dampenY = false;
        if (speedY > -MAX_SPEED) {
            speedY -= SPEED_INCREMENT;
        }
    }

    public void registerInputActions(Keyboard keyboard) {
        keyboard.registerKeyPressAction(Controls.PLAYER_MOVE_UP, this::accelerateUp);
        keyboard.registerKeyPressAction(Controls.PLAYER_MOVE_DOWN, this::accelerateDown);
        keyboard.registerKeyPressAction(Controls.PLAYER_MOVE_LEFT, this::accelerateLeft);
        keyboard.registerKeyPressAction(Controls.PLAYER_MOVE_RIGHT, this::accelerateRight);
    }

    public void movePlayer(float speedModifier) {
        x += speedX * speedModifier; //TODO shall the Gdx.graphics.getDeltaTime() should be factored in?
        roundX = (int) x;
        y += speedY * speedModifier; //TODO shall the Gdx.graphics.getDeltaTime() should be factored in?
        roundY = (int) y;

        boolean moved = speedX != 0 || speedY != 0;
        if (moved) {
            playerSprite.setRotation(360f - (float)(Math.atan2(speedX, speedY) * 180 / Math.PI));
        }

        if (speedX != 0 && dampenX) {
            speedX = speedX > 0 ? speedX - SPEED_DECREMENT : speedX + SPEED_DECREMENT;
        }

        if (speedY != 0 && dampenY) {
            speedY = speedY > 0 ? speedY - SPEED_DECREMENT : speedY + SPEED_DECREMENT;
        }
        dampenX = true;
        dampenY = true;
    }

    public void dispose() {
        playerTexture.dispose();
    }
}
