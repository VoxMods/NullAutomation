/**
 * Created by Benjamin Ward on 8/5/2016.
 */
package com.voxmods.nullautomation.client.render.interfaces;


import javax.annotation.Nullable;

public interface IWidgetIcon {

    int getX();
    int getY();
    int getWidth();
    int getHeight();

    @Nullable
    IWidgetIcon getOverlay();

    IWidgetMap getMap();
}