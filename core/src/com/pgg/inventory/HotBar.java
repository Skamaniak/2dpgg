package com.pgg.inventory;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.pgg.input.Controls;
import com.pgg.input.Keyboard;
import com.pgg.inventory.item.Item;

public class HotBar {
    private static final int INVENTORY_BOARDER_THICKNESS = 1;
    private static final int ITEM_BOARDER_THICKNESS = 3;
    private static final int INVENTORY_SLOT_BOARDER_THICKNESS = 5;
    private static final int INVENTORY_SLOT_SIZE = 32;

    private final Texture handInventoryTexture = new Texture("inventory-hand.png");
    private final Texture selectedSlotTexture = new Texture("inventory-selected-slot.png");
    private final OrthographicCamera camera;
    private final Item[] items = new Item[10];
    private int selectedSlot = 0;


    public HotBar(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void render(Batch batch) {
        float xInvOffset = -camera.viewportWidth / 2;
        float yInvOffset = (camera.viewportHeight / 2) - handInventoryTexture.getHeight();
        batch.draw(handInventoryTexture, xInvOffset, yInvOffset);


        float ySlotOffset = (camera.viewportHeight / 2) - selectedSlotTexture.getHeight() - INVENTORY_BOARDER_THICKNESS;
        batch.draw(selectedSlotTexture, computeSlotX(selectedSlot), ySlotOffset);

        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                batch.draw(items[i].getTexture(), computeItemX(i) , ySlotOffset);
            }
        }
    }

    private float computeSlotX(int slot) {
        float slotOffsetX = (-camera.viewportWidth / 2) + INVENTORY_BOARDER_THICKNESS;
        return slotOffsetX + (INVENTORY_SLOT_SIZE + INVENTORY_SLOT_BOARDER_THICKNESS) * slot;
    }

    private float computeItemX(int slot) {
        float slotOffsetX = (-camera.viewportWidth / 2) + ITEM_BOARDER_THICKNESS;
        return slotOffsetX + (INVENTORY_SLOT_SIZE + INVENTORY_SLOT_BOARDER_THICKNESS) * slot;
    }

    private void setSelectedSlot(int slot) {
        this.selectedSlot = slot;
    }

    public void setItem(int slot, Item item) {
        items[slot] = item;
    }

    public Item getSelectedItem() {
        return items[selectedSlot];
    }

    public void registerInputActions(Keyboard keyboard) {
        keyboard.registerKeyPressAction(Controls.INVENTORY_SELECT_SLOT_1, () -> setSelectedSlot(0));
        keyboard.registerKeyPressAction(Controls.INVENTORY_SELECT_SLOT_2, () -> setSelectedSlot(1));
        keyboard.registerKeyPressAction(Controls.INVENTORY_SELECT_SLOT_3, () -> setSelectedSlot(2));
        keyboard.registerKeyPressAction(Controls.INVENTORY_SELECT_SLOT_4, () -> setSelectedSlot(3));
        keyboard.registerKeyPressAction(Controls.INVENTORY_SELECT_SLOT_5, () -> setSelectedSlot(4));
        keyboard.registerKeyPressAction(Controls.INVENTORY_SELECT_SLOT_6, () -> setSelectedSlot(5));
        keyboard.registerKeyPressAction(Controls.INVENTORY_SELECT_SLOT_7, () -> setSelectedSlot(6));
        keyboard.registerKeyPressAction(Controls.INVENTORY_SELECT_SLOT_8, () -> setSelectedSlot(7));
        keyboard.registerKeyPressAction(Controls.INVENTORY_SELECT_SLOT_9, () -> setSelectedSlot(8));
        keyboard.registerKeyPressAction(Controls.INVENTORY_SELECT_SLOT_10, () -> setSelectedSlot(9));
    }

    public void dispose() {
        handInventoryTexture.dispose();
        selectedSlotTexture.dispose();
    }
}
