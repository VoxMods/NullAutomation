/**
 * Created by Benjamin Ward on 8/4/2016.
 */
package com.voxmods.nullautomation.machine.gate.energygate;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import com.voxmods.nullautomation.util.Constants;

public class GuiEnergyGate extends GuiContainer {
    protected static final String titleString = "text." + Constants.Mod.ID + ".machineEnergyGate.title";

    protected ResourceLocation guiTextureLocation = new ResourceLocation(Constants.Mod.DOMAIN + "textures/gui/machineEnergyGate.png");
    protected InventoryPlayer player;
    protected TileEnergyGate tile;

    public GuiEnergyGate(TileEnergyGate tileEntity, InventoryPlayer inventory)
    {
        super(new ContainerEnergyGate(tileEntity, inventory));
        this.player = inventory;
        this.tile = tileEntity;
        this.ySize = 165; // Arbitrary
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j)
    {
        String name = I18n.format(titleString);
        mc.fontRendererObj.drawString(name, (xSize - mc.fontRendererObj.getStringWidth(name)) / 2, 6, 0x404040);
        mc.fontRendererObj.drawString(I18n.format(this.player.getName()), 8, ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        mc.renderEngine.bindTexture(guiTextureLocation);
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        /*for (int s = 0; s < tile.dryTimeRemaining.length; s++)
        {
            int mt = Dryable.getDryingTime(inventorySlots.getSlot(s).getStack());
            int ct = tile.dryTimeRemaining[s];

            if (ct > 0 && mt > 0)
            {
                int sx = x + 44 + 36 * s;
                int ny = (int) Math.ceil(ct * 20.0 / mt);
                int sy = 20 - ny;

                this.drawTexturedModalRect(sx, y + 32 + sy, 176, sy, 9, ny);
            }
        }*/
    }
}
