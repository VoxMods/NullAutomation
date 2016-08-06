/**
 * Created by Benjamin Ward on 8/4/2016.
 */
package com.voxmods.nullautomation.util.gui;

import com.voxmods.nullautomation.machine.gate.energygate.ContainerEnergyGate;
import com.voxmods.nullautomation.machine.gate.energygate.GuiEnergyGate;
import com.voxmods.nullautomation.machine.gate.energygate.TileEnergyGate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{
    public static final int GUI_ENERGY_GATE = 0;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

        switch (id)
        {
            case GUI_ENERGY_GATE:
                if (tileEntity instanceof TileEnergyGate)
                {
                    return new ContainerEnergyGate((TileEnergyGate) tileEntity, player.inventory);
                }
                break;
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

        switch (id)
        {
            case GUI_ENERGY_GATE:
                if (tileEntity instanceof TileEnergyGate)
                {
                    return new GuiEnergyGate((TileEnergyGate) tileEntity, player.inventory);
                }
                break;
        }

        return null;
    }
}
