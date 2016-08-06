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
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class MachineEntity extends TileEntity implements ITickable {
    private FlawInventory inventory;
    protected int flawID = 0;

    protected FlawInventory getFlaw(int id) {

        if(id < 0)
            return null;
        if(inventory == null || inventory.id != id)
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

    @Override
    public SPacketUpdateTileEntity getUpdatePacket () {

        return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag () {

        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket (NetworkManager net, SPacketUpdateTileEntity packet) {

        super.onDataPacket(net, packet);
        this.readFromNBT(packet.getNbtCompound());
    }
}
