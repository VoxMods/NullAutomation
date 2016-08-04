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
package com.voxmods.nullautomation.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Map;

public class FlawStorageWorldData extends WorldSavedData {
    private static final String StorageKey = "flawStorageManager";

    private Map<Integer, FlawInventory> flaws = new HashMap<Integer, FlawInventory>();
    private int lastFlawId;

    public FlawStorageWorldData()
    {
        super(StorageKey);
    }

    public FlawStorageWorldData(String key)
    {
        super(key);
    }

    public static FlawStorageWorldData get(World world)
    {
        MapStorage storage = world.getMapStorage();
        FlawStorageWorldData instance = (FlawStorageWorldData) storage.getOrLoadData(FlawStorageWorldData.class, StorageKey);
        if (instance == null)
        {
            instance = new FlawStorageWorldData();
            storage.setData(StorageKey, instance);
        }

        return instance;
    }

    public FlawInventory getFlaw(int id)
    {
        FlawInventory flaw = flaws.get(id);

        if (flaw == null)
        {
            flaw = new FlawInventory(this);
            flaws.put(id, flaw);
        }

        return flaw;
    }

    public int getNextflawId()
    {
        markDirty();

        return ++lastFlawId;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        NBTTagList nbtTagList = nbtTagCompound.getTagList("Rifts", Constants.NBT.TAG_COMPOUND);

        flaws.clear();

        for (int i = 0; i < nbtTagList.tagCount(); ++i)
        {
            NBTTagCompound nbtTagCompound1 = nbtTagList.getCompoundTagAt(i);
            int j = nbtTagCompound1.getByte("Flaw");

            FlawInventory inventory = new FlawInventory(this);
            inventory.readFromNBT(nbtTagCompound1);

            flaws.put(j, inventory);
        }

        lastFlawId = nbtTagCompound.getInteger("LastFlawId");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound)
    {
        NBTTagList nbtTagList = new NBTTagList();

        for (Map.Entry<Integer, FlawInventory> entry : flaws.entrySet())
        {
            FlawInventory inventory = entry.getValue();

            NBTTagCompound nbtTagCompound1 = inventory.writeToNBT(new NBTTagCompound());
            nbtTagCompound1.setInteger("Flaw", entry.getKey());
            nbtTagList.appendTag(nbtTagCompound1);
        }

        nbtTagCompound.setTag("Flaws", nbtTagList);
        nbtTagCompound.setInteger("LastFlawId", lastFlawId);

        return nbtTagCompound;
    }
}
