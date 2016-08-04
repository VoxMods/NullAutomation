package com.voxmods.nullautomation.registry;

import com.voxmods.nullautomation.block.ModBlock;
import com.voxmods.nullautomation.machine.gate.energygate.BlockEnergyGate;
import com.voxmods.nullautomation.util.Constants;
import com.voxmods.nullautomation.util.helpers.RenderHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.*;

public class ModBlocks {
    public static final BlockEnergyGate ENERGY_GATE;

    private static final Set<Block> MOD_BLOCKS = new HashSet<>();

    static {
        ENERGY_GATE = (BlockEnergyGate) registerBlock(new BlockEnergyGate(), true);
    }

    public static void init() {
        // Dummy method to make sure static initializer runs
    }

    public static void initRenders() {
        for (Block block : MOD_BLOCKS) {
            Item item = Item.getItemFromBlock(block);
            if (item != null) {
                System.out.println(item.getRegistryName().getResourcePath());
                RenderHelper.registerRender(item, 0, item.getRegistryName().getResourcePath(), "inventory");
            }
        }
    }

    private static Block registerBlock(ModBlock block, boolean registerItem) {
        ResourceLocation registryName = block.getRegistryName();
        if (registryName == null) {
            registryName = block
                    .setRegistryName(new ResourceLocation(Constants.Mod.DOMAIN + block.getModName()))
                    .getRegistryName();
        }

        GameRegistry.register(block);

        if (registerItem) {
            ItemBlock item = (ItemBlock) (new ItemBlock(block).setRegistryName(registryName));
            GameRegistry.register(item);
        }

        System.out.println(registryName);

        MOD_BLOCKS.add(block);

        return block;
    }
}
