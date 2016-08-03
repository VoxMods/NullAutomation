/**
 * This file is part of Null Automation.
 * Copyright (c) 2016 Benjamin Ward, All rights reserved.
 * <p>
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 * <p>
 * Created by Vox (Benjamin Ward) on 5/12/2016.
 */
package com.voxmods.nullautomation.storage;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.List;

public class FlawInventory {
    private final FlawStorageWorldData manager;

    private final List<ItemStack> inventorySlots = Lists.newArrayList();
    private long Energy;

    FlawInventory(FlawStorageWorldData manager) {
        this.manager = manager;
    }

    private void onContentsChanged() {
        manager.markDirty();
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        Energy = nbtTagCompound.getLong("Energy");
        NBTTagList itemList = nbtTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        readItems(itemList);
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setLong("Energy", Energy);
        nbtTagCompound.setTag("Items", writeItems());
    }

    private void readItems(NBTTagList itemList) {
        inventorySlots.clear();

        for (int i = 0; i < itemList.tagCount(); ++i) {
            NBTTagCompound slot = itemList.getCompoundTagAt(i);
            inventorySlots.add(ItemStack.loadItemStackFromNBT(slot));
        }
    }

    private NBTTagList writeItems()
    {
        NBTTagList itemList = new NBTTagList();

        inventorySlots.stream().filter(stack -> stack != null).forEach(stack -> {
            NBTTagCompound slot = new NBTTagCompound();
            stack.writeToNBT(slot);
            itemList.appendTag(slot);
        });

        return itemList;
    }

    /**
     * Remove energy from a FlawInventory.
     *
     * @param maxExtract
     *            Maximum amount of energy to extract.
     * @param simulate
     *            If TRUE, the extraction will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted.
     */
    int extractEnergy(int maxExtract, boolean simulate)
    {
        if(!simulate)
            onContentsChanged();
        return 0;
    }

    /**
     * Add energy to a FlawInventory.
     *
     * @param maxReceive
     *            Maximum amount of energy to receive.
     * @param simulate
     *            If TRUE, the charge will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) received.
     */
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if(!simulate)
            onContentsChanged();
        return 0;
    }

    /**
     * Returns the amount of energy currently stored.
     *
     * @return Amount of energy that is currently stored.
     */
    public int getEnergyStored() {
        return 0;
    }

    /**
     * Returns the maximum amount of energy that can be stored.
     *
     * @return Maximum amount of energy that can be stored.
     */
    public int getMaxEnergyStored() {
        return 0;
    }

    public int getSlots() {
        return inventorySlots.size();
    }

    public ItemStack getStackInSlot(int index) {
        return inventorySlots.get(index);
    }

    public ItemStack insertItems(@Nonnull ItemStack stack) {
        ItemStack remaining = stack.copy();

        // Try to fill existing slots first
        for (ItemStack slot : inventorySlots) {
            if (slot != null) {
                int max = Math.min(remaining.getMaxStackSize(), 64);
                int transfer = Math.min(remaining.stackSize, max - slot.stackSize);
                if (transfer > 0 && ItemStack.areItemsEqual(slot, remaining) && ItemStack.areItemStackTagsEqual(slot, remaining)) {
                    slot.stackSize += transfer;
                    remaining.stackSize -= transfer;
                    if (remaining.stackSize <= 0)
                        break;
                }
            }
        }

        // Then place any remaining items in the first available empty slot
        if (remaining.stackSize > 0)
            inventorySlots.add(remaining);

        // FIXME: There is no maximum inventory size (if there was, the above statement would
        // FIXME: need to be revised to check that, and any remaining items would have to be returned).

        onContentsChanged();

        return null;
    }

    public ItemStack extractItems(@Nonnull ItemStack stack, int wanted, boolean simulate) {
        ItemStack extracted = stack.copy();
        extracted.stackSize = 0;

        if (stack.stackSize <= 0)
            return null;

        for (int i = 0; i < inventorySlots.size(); i++) {
            ItemStack slot = inventorySlots.get(i);
            if (slot != null) {
                int available = Math.min(wanted, slot.stackSize);
                if (available > 0 && ItemStack.areItemsEqual(slot, stack) && ItemStack.areItemStackTagsEqual(slot, stack)) {
                    extracted.stackSize += available;

                    if (!simulate) {
                        slot.stackSize -= available;
                        if (slot.stackSize <= 0)
                            inventorySlots.remove(i);
                    }

                    wanted -= available;
                    if (wanted <= 0)
                        break;
                }
            }
        }

        if (extracted.stackSize <= 0)
            return null;

        onContentsChanged();
        return extracted;
    }
}
