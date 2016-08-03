/**
 * This file is part of Null Automation.
 * Copyright (c) 2016 Benjamin Ward, All rights reserved.
 * <p>
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 * <p>
 * Created by Vox (Benjamin Ward) on 5/8/2016.
 */
package com.voxmods.nullautomation.machine;

import net.minecraft.nbt.NBTTagCompound;

public abstract class PoweredMachineEntity extends MachineEntity {
    public MachineEnergyBuffer EnergyBuffer;

    public PoweredMachineEntity(int capacity, int maxTransfer)
    {
        EnergyBuffer = new MachineEnergyBuffer(capacity, maxTransfer);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        EnergyBuffer.readFromNBT(compound);
        /* Example
        NBTTagList _outputs = compound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < _outputs.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = _outputs.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 255;

            if (j >= 0 && j < inputs.getSlots())
            {
                inputs.setStackInSlot(j, ItemStack.loadIte  mStackFromNBT(nbttagcompound));
            }
        }

        heatLevel = compound.getInteger("heatLevel");
        burnTimeRemaining = compound.getInteger("burnTimeRemaining");
        currentItemBurnTime = compound.getInteger("currentItemBurnTime");
        containedEnergy = compound.getInteger("powerLevel");
        timeInterval = compound.getInteger("timeInterval");
        */
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        EnergyBuffer.writeToNBT(compound);
        /* Example

        NBTTagList _outputs = new NBTTagList();
        for (int i = 0; i < inputs.getSlots(); ++i)
        {
            ItemStack stack = inputs.getStackInSlot(i);
            if (stack != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                stack.writeToNBT(nbttagcompound);
                _outputs.appendTag(nbttagcompound);
            }
        }

        compound.setTag("Items", _outputs);

        compound.setInteger("heatLevel", heatLevel);
        compound.setInteger("burnTimeRemaining", burnTimeRemaining);
        compound.setInteger("currentItemBurnTime", currentItemBurnTime);
        compound.setInteger("powerLevel", containedEnergy);
        compound.setInteger("timeInterval", timeInterval);
        */
    }

    public int receiveEnergy(int maxReceive) {
        return EnergyBuffer.receiveEnergy(maxReceive, false);
    }

    public int getEnergyStored() {
        return EnergyBuffer.getEnergyStored();
    }

    public int getMaxEnergyStored() {
        return EnergyBuffer.getMaxEnergyStored();
    }
}
