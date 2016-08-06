/**
 * Created by Benjamin Ward on 8/5/2016.
 */
package com.voxmods.nullautomation.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class RenderUtil {
    public static TextureManager engine() {
        return Minecraft.getMinecraft().renderEngine;
    }

    public static void bindBlockTexture() {
        engine().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    }

    public static void bindTexture(String string) {
        engine().bindTexture(new ResourceLocation(string));
    }

    public static void bindTexture(ResourceLocation tex) {
        engine().bindTexture(tex);
    }

    public static FontRenderer fontRenderer() {
        return Minecraft.getMinecraft().fontRendererObj;
    }

    private static Field timerField = initTimer();

    private static Field initTimer() {
        Field f = null;
        try {
            f = ReflectionHelper.findField(Minecraft.class, "field_71428_T", "timer", "Q");
            f.setAccessible(true);
        } catch (Exception e) {
            System.out.println("Failed to initialize timer reflection for IO config.");
            e.printStackTrace();
        }
        return f;
    }

    @Nullable
    public static Timer getTimer() {
        if (timerField == null) {
            return null;
        }
        try {
            return (Timer) timerField.get(Minecraft.getMinecraft());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}