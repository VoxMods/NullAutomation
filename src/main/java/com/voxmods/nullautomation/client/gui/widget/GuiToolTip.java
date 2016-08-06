package com.voxmods.nullautomation.client.gui.widget;

import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;

import com.voxmods.nullautomation.client.gui.interfaces.IHidable;


public class GuiToolTip implements IHidable {

    private static final long DELAY = 0;

    protected Rectangle bounds;

    private long mouseOverStart;

    protected final List<String> text;

    private int lastMouseX = -1;

    private int lastMouseY = -1;

    private boolean visible = true;

    public GuiToolTip(Rectangle bounds, String... lines) {
        this.bounds = bounds;
        if (lines != null) {
            text = new ArrayList<String>(lines.length);
            for (String line : lines) {
                text.add(line);
            }
        } else {
            text = new ArrayList<String>();
        }
    }

    public GuiToolTip(Rectangle bounds, List<String> lines) {
        this.bounds = bounds;
        if (lines == null) {
            text = new ArrayList<String>();
        } else {
            text = new ArrayList<String>(lines);
        }
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setIsVisible(boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void onTick(int mouseX, int mouseY) {
        if (lastMouseX != mouseX || lastMouseY != mouseY) {
            mouseOverStart = 0;
        }

        if (isVisible() && getBounds().contains(mouseX, mouseY)) {

            if (mouseOverStart == 0) {
                mouseOverStart = System.currentTimeMillis();
            }
        } else {
            mouseOverStart = 0;
        }

        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }

    public boolean shouldDraw() {
        if (!isVisible()) {
            return false;
        }
        updateText();
        if (mouseOverStart == 0) {
            return false;
        }
        return System.currentTimeMillis() - mouseOverStart >= DELAY;
    }

    protected void updateText() {}

    public void setToolTipText(String... txt) {
        text.clear();
        if (txt != null) {
            for (String line : txt) {
                text.add(line);
            }
        }
    }

    public List<String> getToolTipText() {
        return text;
    }

}