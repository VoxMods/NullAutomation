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

package com.voxmods.nullautomation.interfaces;

import com.voxmods.nullautomation.registry.ModBlocks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {
    public default void preInit(FMLPreInitializationEvent event)
    {
        ModBlocks.init();
        ModBlocks.initRenders();
    }

    public default void init(FMLInitializationEvent event) {
        // NO-OP
    }

    public default void postInit(FMLPostInitializationEvent event)
    {
        // NO-OP
    }
}
