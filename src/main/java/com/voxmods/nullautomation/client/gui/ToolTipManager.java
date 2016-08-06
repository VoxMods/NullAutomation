/**
 * Created by Benjamin Ward on 8/5/2016.
 */
package com.voxmods.nullautomation.client.gui;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import com.voxmods.nullautomation.client.gui.interfaces.IToolTipRenderer;
import com.voxmods.nullautomation.client.gui.widget.GuiToolTip;
import net.minecraft.client.gui.FontRenderer;

public class ToolTipManager {

    private final Set<GuiToolTip> toolTips = Sets.newHashSet();

    public void addToolTip(GuiToolTip toolTip) {
        toolTips.add(toolTip);
    }

    public boolean removeToolTip(GuiToolTip toolTip) {
        return toolTips.remove(toolTip);
    }

    public void clearToolTips() {
        toolTips.clear();
    }

    protected final void drawTooltips(IToolTipRenderer renderer, int mouseX, int mouseY) {
        for (GuiToolTip toolTip : toolTips) {
            toolTip.onTick(mouseX - renderer.getGuiLeft(), mouseY - renderer.getGuiTop());
            if (toolTip.shouldDraw()) {
                drawTooltip(toolTip, mouseX, mouseY, renderer);
            }
        }
    }

    protected void drawTooltip(GuiToolTip toolTip, int mouseX, int mouseY, IToolTipRenderer renderer) {
        List<String> list = toolTip.getToolTipText();
        if (list == null) {
            return;
        }

        List<String> formatted = new ArrayList<String>(list.size());
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                formatted.add("\u00a7f" + list.get(i));
            } else {
                formatted.add("\u00a77" + list.get(i));
            }
        }

        if (mouseX > renderer.getGuiLeft() + renderer.getXSize() / 2) {
            int maxWidth = 0;
            Iterator<String> iterator = formatted.iterator();
            while (iterator.hasNext()) {
                String s = iterator.next();
                int w = renderer.getFontRenderer().getStringWidth(s);
                if (w > maxWidth) {
                    maxWidth = w;
                }
            }
            mouseX -= maxWidth + 18;
        }
        renderer.drawHoveringText(formatted, mouseX, mouseY, renderer.getFontRenderer());
    }
}
