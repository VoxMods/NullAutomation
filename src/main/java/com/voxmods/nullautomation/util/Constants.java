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

package com.voxmods.nullautomation.util;

public class Constants {
    public static final class Mod
    {
        public static final String ID = "nullautomation";
        public static final String NAME = "Null Automation";
        public static final String DOMAIN = "nullautomation:";
        public static final String DEPENDS = "required-after:Forge@[12.16.1.1887,);";
        public static final String VERSION = "@VERSION@";
        public static final String MCVERSION = "@MCVERSION@";
    }
    public static final class Proxy
    {
        public static final String COMMON = "com.voxmods.nullautomation.client.ClientProxy";
        public static final String SERVER = "com.voxmods.nullautomation.server.ServerProxy";
    }
}
