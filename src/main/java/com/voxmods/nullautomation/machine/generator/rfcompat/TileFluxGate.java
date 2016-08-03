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
package com.voxmods.nullautomation.machine.generator.rfcompat;

import cofh.api.energy.IEnergyReceiver;
import com.voxmods.nullautomation.machine.MachineEntity;
import net.minecraft.util.EnumFacing;

public class TileFluxGate extends MachineEntity implements IEnergyReceiver {
    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        return getFlaw().receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return 0;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }
}
