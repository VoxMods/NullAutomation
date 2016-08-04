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
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.List;

public class FlawInventory implements ITeslaProducer, ITeslaConsumer, ITeslaHolder {
    private final FlawStorageWorldData manager;

    /// Energy
    /**
     * The amount of stored Tesla power.
     */
    protected long stored;
    /**
     * The maximum amount of Tesla power that can be stored.
     */
    protected long capacity = 1000000; // TODO: Fix this?
    /**
     * The maximum amount of Tesla power that can be accepted.
     */
    protected long inputRate = 128; // TODO: Fix this?
    /**
     * The maximum amount of Tesla power that can be extracted.
     */
    protected long outputRate = 128; // TODO: Fix this?

    /// Items
    //private final List<ItemStack> inventorySlots = Lists.newArrayList();

    // Liquids
    ///

    FlawInventory(FlawStorageWorldData manager) {
        this.manager = manager;
    }

    private void onContentsChanged() {
        manager.markDirty();

    }

    @Override
    public long getStoredPower () {

        return this.stored;
    }

    @Override
    public long givePower (long Tesla, boolean simulated) {

        final long acceptedTesla = Math.min(this.getCapacity() - this.stored, Math.min(inputRate, Tesla));

        if (!simulated) {
            this.stored += acceptedTesla;
            onContentsChanged();
        }
        return acceptedTesla;
    }

    @Override
    public long takePower (long Tesla, boolean simulated) {

        final long removedPower = Math.min(this.stored, Math.min(outputRate, Tesla));

        if (!simulated) {
            this.stored -= removedPower;
            onContentsChanged();
        }
        return removedPower;
    }

    @Override
    public long getCapacity () {

        return this.capacity;
    }

    private NBTTagCompound serializeEnergyInfoNBT () {

        final NBTTagCompound dataTag = new NBTTagCompound();
        dataTag.setLong("TeslaPower", this.stored);
        dataTag.setLong("TeslaCapacity", this.capacity);
        dataTag.setLong("TeslaInput", this.inputRate);
        dataTag.setLong("TeslaOutput", this.outputRate);
        return dataTag;
    }

    private void deserializeEnergyInfoNBT(NBTTagCompound nbt) {

        this.stored = nbt.getLong("TeslaPower");
        if (nbt.hasKey("TeslaCapacity"))
            this.capacity = nbt.getLong("TeslaCapacity");
        if (nbt.hasKey("TeslaInput"))
            this.inputRate = nbt.getLong("TeslaInput");
        if (nbt.hasKey("TeslaOutput"))
            this.outputRate = nbt.getLong("TeslaOutput");
        if (this.stored > this.capacity)
            this.stored = this.capacity;
    }


    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        deserializeEnergyInfoNBT(nbtTagCompound.getCompoundTag("EnergyInfo"));
        //NBTTagList itemList = nbtTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        //readItems(itemList);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setTag("EnergyInfo", serializeEnergyInfoNBT());
        //nbtTagCompound.setTag("Items", writeItems());
        return nbtTagCompound;
    }

    /*
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
    */
}
