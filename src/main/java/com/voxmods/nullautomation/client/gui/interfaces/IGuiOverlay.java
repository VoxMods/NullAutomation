/**
 * Created by Benjamin Ward on 8/5/2016.
 */
package com.voxmods.nullautomation.client.gui.interfaces;

import java.awt.*;

public interface IGuiOverlay extends IHidable {
    void init(IGuiWindow screen);
    Rectangle getBounds();
    void draw(int mouseX, int mouseY, float partialTick);
    boolean handleMouseInput(int x, int y, int b);
    boolean isMouseInBounds(int mouseX, int mouseY);
    void guiClosed();
}
