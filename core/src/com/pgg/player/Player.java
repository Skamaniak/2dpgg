package com.pgg.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pgg.input.Controls;
import com.pgg.input.Keyboard;

public class Player {
    private static final int MAX_SPEED = 256;
    private static final int SPEED_INCREMENT = 32;
    private static final int SPEED_DECREMENT = 16;
    private static final int[] ANIMATION_FRAMES = new int[] {0, 1, 2, 1};
    private static final int PX_PER_ANIM_FRAME = 32;
    private final TextureRegion[][] playerTextures;
    private TextureRegion currentPlayerTexture;
    public float x;
    public float y;

    public int roundX;
    public int roundY;

    private int speedX = 0;
    private int speedY = 0;

    private float animFrameAccumulator = 0;

    private boolean dampenX = true;
    private boolean dampenY = true;

    public Player() {
        playerTextures = new TextureRegion(new Texture("player.png")).split(32, 32); //TODO use AssetManager?
        currentPlayerTexture = playerTextures[0][0];
    }

    public void render(Batch batch) {
        batch.draw(currentPlayerTexture, x - 16, y);
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
        float totalSpeedX = speedX * speedModifier * Gdx.graphics.getDeltaTime();
        float totalSpeedY = speedY * speedModifier * Gdx.graphics.getDeltaTime();
        x += totalSpeedX;
        y += totalSpeedY;
        roundX = (int) x;
        roundY = (int) y;

        boolean moved = speedX != 0 || speedY != 0;
        if (moved) {
            animateMovement(totalSpeedX, totalSpeedY);
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

    private void animateMovement(float totalSpeedX, float totalSpeedY) {
        double angle = (Math.atan2(totalSpeedX, totalSpeedY) + Math.PI) / Math.PI * 2;
        int direction;
        if (angle > 1.5 && angle < 2.5) {
            direction = 3; //up
        } else if (angle >= 2.5 && angle <= 3.5) {
            direction = 2; //right
        } else if (angle >= 0.5 && angle <= 1.5) {
            direction = 1; //left
        } else {
            direction = 0; //down
        }

        animFrameAccumulator += Math.abs(totalSpeedX) + Math.abs(totalSpeedY);
        int frame = (int)(animFrameAccumulator / PX_PER_ANIM_FRAME) % ANIMATION_FRAMES.length;
        currentPlayerTexture = playerTextures[direction][ANIMATION_FRAMES[frame]];
    }

    @Override
    public String toString() {
        return "Player{" +
                "x=" + x +
                ", y=" + y +
                ", roundX=" + roundX +
                ", roundY=" + roundY +
                ", speedX=" + speedX +
                ", speedY=" + speedY +
                ", animFrameAccumulator=" + animFrameAccumulator +
                ", dampenX=" + dampenX +
                ", dampenY=" + dampenY +
                '}';
    }
}
