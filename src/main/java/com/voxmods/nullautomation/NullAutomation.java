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

package com.voxmods.nullautomation;

import com.voxmods.nullautomation.util.Constants;
import com.voxmods.nullautomation.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.Mod.ID, name = Constants.Mod.NAME, version = Constants.Mod.VERSION, dependencies = Constants.Mod.DEPENDS)
public class NullAutomation {
    @Mod.Instance
    public static NullAutomation instance;

    @SidedProxy(clientSide = Constants.Proxy.COMMON, serverSide = Constants.Proxy.SERVER)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
