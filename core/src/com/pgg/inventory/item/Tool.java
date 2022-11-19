package com.pgg.inventory.item;

import com.badlogic.gdx.graphics.Texture;

public enum Tool implements Item {
    AXE("item/axe.png"),
    PICKAXE("item/pickaxe.png");

    private final Texture texture;

    Tool(String texturePath) {
        texture = new Texture(texturePath);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
