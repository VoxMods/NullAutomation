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

import com.voxmods.nullautomation.storage.FlawInventory;
import com.voxmods.nullautomation.storage.FlawStorageWorldData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class MachineEntity extends TileEntity implements ITickable {
    private FlawInventory inventory;
    protected int flawID = 0;

    protected FlawInventory getFlaw(int id)
    {
        if(id < 0)
            return null;
        if(inventory == null)
            inventory = FlawStorageWorldData.get(worldObj).getFlaw(id);
        return inventory;
    }

    // TODO: Get rid of this after testing
    @Deprecated
    protected FlawInventory getFlaw()
    {
        return getFlaw(flawID);
    }

    @Override
    public void update() {
    }
}
