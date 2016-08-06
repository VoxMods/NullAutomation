/**
 * Created by Benjamin Ward on 8/4/2016.
 */
package com.voxmods.nullautomation.machine.gate.energygate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerEnergyGate extends Container {

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return false;
    }
}
