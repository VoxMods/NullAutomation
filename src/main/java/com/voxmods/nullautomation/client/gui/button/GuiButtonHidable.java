/**
 * Created by Benjamin Ward on 8/5/2016.
 */
package com.voxmods.nullautomation.client.gui.button;

import com.voxmods.nullautomation.client.gui.interfaces.IHidable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonHidable extends GuiButton implements IHidable {
    public GuiButtonHidable(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    public GuiButtonHidable(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void setIsVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    protected int getHoverState(boolean mouseOver) {
        int b = super.getHoverState(mouseOver);
        if (!isEnabled()) {
            b = 0;
        }
        return b;
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return isEnabled() && super.mousePressed(mc, mouseX, mouseY);
    }
}