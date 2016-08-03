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

import com.voxmods.nullautomation.block.ModBlock;
import com.voxmods.nullautomation.util.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFluxGate extends ModBlock {
    public BlockFluxGate()
    {
        super();
        setUnlocalizedName(Constants.Mod.ID + "." + getModName());
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }
    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileFluxGate();
    }

    @Override
    public String getModName()
    {
        return "machineFluxGate";
    }
}
