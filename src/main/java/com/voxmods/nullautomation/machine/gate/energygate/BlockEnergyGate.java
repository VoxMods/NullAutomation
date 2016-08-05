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
package com.voxmods.nullautomation.machine.gate.energygate;

import com.voxmods.nullautomation.NullAutomation;
import com.voxmods.nullautomation.block.ModBlock;
import com.voxmods.nullautomation.util.Constants;
import com.voxmods.nullautomation.util.GuiHandler;
import com.voxmods.nullautomation.util.TextUtils;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockEnergyGate extends ModBlock {
    public BlockEnergyGate()
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
        return new TileEnergyGate();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (!(tileEntity instanceof TileEnergyGate))
            return false;

        // Check for info tool here
        if(playerIn.isSneaking())  {
            // NOTE: Flaw only knows about power on the server, but this method is called both on server and client.
            if(!worldIn.isRemote)
                {
                if (tileEntity.hasCapability(TeslaCapabilities.CAPABILITY_HOLDER, side)) {
                    final int MESSAGE_ID = 14940026;
                    final ITeslaHolder holder = tileEntity.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, side);
                    // TODO: put msg id in info tool, check flaw id instead of 0
                    TextUtils.sendSpamlessMessage(MESSAGE_ID, new TextComponentString(I18n.format("tooltip.nullautomation.machineEnergyGate.analysis", 0, holder.getStoredPower(), holder.getCapacity())));
                }
            }
        }
        else {
            playerIn.openGui(NullAutomation.instance, GuiHandler.GUI_ENERGY_GATE, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    public String getModName()
    {
        return "machineEnergyGate";
    }
}
