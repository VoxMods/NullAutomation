/**
 * Created by Benjamin Ward on 8/4/2016.
 */
package com.voxmods.nullautomation.machine.gate.energygate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerEnergyGate extends Container {
    protected TileEnergyGate tile;

    public ContainerEnergyGate(TileEnergyGate tileEntity, InventoryPlayer playerInventory) {
        this.tile = tileEntity;
        // addSlotToContainer(new SlotItemHandler(tile.items, index, x, y));
        // bindPlayerInventory(playerInventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.isUsableByPlayer(playerIn);
    }
    /*
    protected void bindPlayerInventory(InventoryPlayer playerInventory) {
        for (int y = 0; y < 3; y++)
        {
            for (int x = 0; x < 9; x++)
            {
                addSlotToContainer(new Slot(playerInventory,
                        x + y * 9 + 9,
                        8 + x * 18, 84 + y * 18));
            }
        }

        for (int x = 0; x < 9; x++)
        {
            addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 142));
        }
    }*/
}
