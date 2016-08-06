/**
 * Created by Benjamin Ward on 8/5/2016.
 */
package com.voxmods.nullautomation.client.gui.interfaces;

import com.voxmods.nullautomation.client.gui.widget.GuiToolTip;
import net.minecraft.client.gui.GuiButton;

import java.io.IOException;

public interface IGuiWindow {
    void addToolTip(GuiToolTip toolTip);
    boolean removeToolTip(GuiToolTip toolTip);
    void clearToolTips();
    int getGuiLeft();
    int getGuiTop();
    int getXSize();
    int getYSize();
    void addButton(GuiButton button);
    void removeButton(GuiButton button);
    int getOverlayOffsetX();
    void doActionPerformed(GuiButton but) throws IOException;
}
