/**
 * Created by Benjamin Ward on 8/5/2016.
 */
package com.voxmods.nullautomation.client.gui.interfaces;

import net.minecraft.client.gui.FontRenderer;

import java.util.List;

public interface IToolTipRenderer {
    int getGuiLeft();
    int getGuiTop();
    int getXSize();
    FontRenderer getFontRenderer();
    void drawHoveringText(List<String> par1List, int par2, int par3, FontRenderer font);
}
