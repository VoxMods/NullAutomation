/**
 * Created by Benjamin Ward on 8/5/2016.
 */
package com.voxmods.nullautomation.client.gui.button;

import java.awt.Rectangle;

import com.voxmods.nullautomation.client.gui.GuiContainerWindow;
import com.voxmods.nullautomation.client.gui.widget.GuiToolTip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;

public class ToolTipButton extends GuiButtonHidable {

    protected int xOrigin;
    protected int yOrigin;
    protected GuiContainerWindow gui;
    protected String[] toolTipText;
    protected GuiToolTip toolTip;

    public ToolTipButton(GuiContainerWindow gui, int id, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(id, x, y, widthIn, heightIn, buttonText);
        this.gui = gui;
        this.xOrigin = x;
        this.yOrigin = y;
    }

    public void setToolTip(String... tooltipText) {
        if (toolTip == null) {
            toolTip = new GuiToolTip(getBounds(), tooltipText);
        } else {
            toolTip.setToolTipText(tooltipText);
        }
        this.toolTipText = tooltipText;
    }

    protected void setToolTip(GuiToolTip newToolTip) {
        boolean addTooltip = false;
        if (toolTip != null) {
            addTooltip = gui.removeToolTip(toolTip);
        }
        toolTip = newToolTip;
        if (addTooltip && toolTip != null) {
            gui.addToolTip(toolTip);
        }
    }

    public final Rectangle getBounds() {
        return new Rectangle(xOrigin, yOrigin, getWidth(), getHeight());
    }

    public void onGuiInit() {
        gui.addButton(this);
        if (toolTip != null) {
            gui.addToolTip(toolTip);
        }
        xPosition = xOrigin + gui.getGuiLeft();
        yPosition = yOrigin + gui.getGuiTop();
    }

    public void detach() {
        gui.removeToolTip(toolTip);
        gui.removeButton(this);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GuiToolTip getToolTip() {
        return toolTip;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        updateTooltipBounds();
    }

    public ToolTipButton setPosition(int x, int y) {
        xOrigin = x;
        yOrigin = y;
        updateTooltipBounds();
        return this;
    }

    public void setXOrigin(int xOrigin) {
        this.xOrigin = xOrigin;
    }

    public void setYOrigin(int yOrigin) {
        this.yOrigin = yOrigin;
    }

    private void updateTooltipBounds() {
        if (toolTip != null) {
            toolTip.setBounds(new Rectangle(xOrigin, yOrigin, width, height));
        }
    }

    protected void updateTooltip(Minecraft mc, int mouseX, int mouseY) {
        if (toolTip != null) {
            toolTip.setIsVisible(visible && enabled);
        }
    }

    protected final void doDrawButton(Minecraft mc, int mouseX, int mouseY) {
        super.drawButton(mc, mouseX, mouseY);
    }

    /**
     * Draws this button to the screen.
     */
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        updateTooltip(mc, mouseX, mouseY);
        doDrawButton(mc, mouseX, mouseY);
    }
}