package com.voxmods.nullautomation.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Created by Benjamin Ward on 5/12/16.
 */
public abstract class ModBlock extends Block {
    public ModBlock()
    {
        super(Material.ROCK);
    }
    public abstract String getModName();
}
