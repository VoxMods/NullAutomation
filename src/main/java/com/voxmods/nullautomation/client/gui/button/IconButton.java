/**
 * Created by Benjamin Ward on 8/5/2016.
 */
package com.voxmods.nullautomation.client.gui.button;

import com.voxmods.nullautomation.client.gui.GuiContainerWindow;
import com.voxmods.nullautomation.client.render.Widgets;
import com.voxmods.nullautomation.client.render.interfaces.IWidgetIcon;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;


public class IconButton extends ToolTipButton {

    public static final int DEFAULT_WIDTH = 16;
    public static final int DEFAULT_HEIGHT = 16;

    protected IWidgetIcon icon;

    private int marginY = 0;
    private int marginX = 0;

    public IconButton(GuiContainerWindow gui, int id, int x, int y, IWidgetIcon icon) {
        super(gui, id, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, "");
        this.icon = icon;
    }

    @Override
    public IconButton setPosition(int x, int y) {
        super.setPosition(x, y);
        return this;
    }

    public IconButton setIconMargin(int x, int y) {
        marginX = x;
        marginY = y;
        return this;
    }

    public IWidgetIcon getIcon() {
        return icon;
    }

    public void setIcon(IWidgetIcon icon) {
        this.icon = icon;
    }

    /**
     * Override this to handle mouse clicks with other buttons than the left
     *
     * @param mc     The MC instance
     * @param x      X coordinate of mouse click
     * @param y      Y coordinate of mouse click
     * @param button the mouse button - only called for button {@literal >}= 1
     * @return true if the mouse click is handled
     */
    public boolean mousePressedButton(Minecraft mc, int x, int y, int button) {
        return false;
    }

    protected boolean checkMousePress(Minecraft mc, int x, int y) {
        // call super here so that we only get the area check
        return super.mousePressed(mc, x, y);
    }

    /**
     * Draws this button to the screen.
     */
    @SuppressWarnings("synthetic-access")
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        updateTooltip(mc, mouseX, mouseY);
        if (isVisible()) {

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
            int hoverState = getHoverState(hovered);
            mouseDragged(mc, mouseX, mouseY);

            IWidgetIcon background = getIconForHoverState(hoverState);

            GL11.glColor3f(1, 1, 1);

            int x = xPosition;
            int y = yPosition;

            GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            background.getMap().render(background, x, y, width, height, 0, true);
            if (icon != null) {
                icon.getMap().render(icon, x + marginX, y + marginY, width - 2 * marginX, height - 2 * marginY, 0, true);
            }

            GL11.glPopAttrib();
        }
    }

    protected IWidgetIcon getIconForHoverState(int hoverState) {
        if (hoverState == 0) {
            return Widgets.BUTTON_DISABLED;
        }
        if (hoverState == 2) {
            return Widgets.BUTTON_HIGHLIGHT;
        }
        return Widgets.BUTTON;
    }
}