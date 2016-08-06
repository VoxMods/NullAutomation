/**
 * Created by Benjamin Ward on 8/5/2016.
 */
package com.voxmods.nullautomation.client.gui;

import com.google.common.collect.Lists;
import com.voxmods.nullautomation.client.gui.button.IconButton;
import com.voxmods.nullautomation.client.gui.interfaces.IGuiOverlay;
import com.voxmods.nullautomation.client.gui.interfaces.IGuiWindow;
import com.voxmods.nullautomation.client.gui.interfaces.IToolTipRenderer;
import com.voxmods.nullautomation.client.gui.widget.GuiToolTip;
import com.voxmods.nullautomation.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Timer;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class GuiContainerWindow extends GuiContainer implements IGuiWindow, IToolTipRenderer {

    protected ToolTipManager toolTipManager = new ToolTipManager();
    protected List<IGuiOverlay> overlays = Lists.newArrayList();
    //protected List<CTextField> textFields = Lists.newArrayList();
    //protected List<VScrollbar> scrollbars = Lists.newArrayList();

    public GuiContainerWindow(Container container)
    {
        super(container);
    }

        public void initGui() {
            super.initGui();
            for (IGuiOverlay overlay : overlays) {
                overlay.init(this);
            }
            //for (CTextField f : textFields) {
                //f.init(this);
            //}
        }


        @Override
        protected void keyTyped(char c, int key) throws IOException {
            //CTextField focused = null;
            //for (CTextField f : textFields) {
                //if (f.isFocused()) {
                    //focused = f;
                //}
            //}

            // If esc is pressed
            if (key == 1) {
                // If there is a focused text field unfocus it
                //if (focused != null && key == 1) {
                    //focused.setFocused(false);
                    //focused = null;
                    //return;
                //} else
                if (!hideOverlays()) { // Otherwise close overlays/GUI
                    this.mc.thePlayer.closeScreen();
                    return;
                }
            }

            // If the user pressed tab, switch to the next text field, or unfocus if there are none
            //if (c == '\t') {
                //for (int i = 0; i < textFields.size(); i++) {
                    //CTextField f = textFields.get(i);
                    //if (f.isFocused()) {
                        //textFields.get((i + 1) % textFields.size()).setFocused(true);
                        //f.setFocused(false);
                        //return;
                    //}
                //}
            //}

            // If there is a focused text field, attempt to type into it
            //if (focused != null) {
                //String old = focused.getText();
                //if (focused.textboxKeyTyped(c, key)) {
                    //onTextFieldChanged(focused, old);
                    //return;
                //}
            //}

            // More NEI behavior, f key focuses first text field
            //if (c == 'f' && focused == null && !textFields.isEmpty()) {
                //focused = textFields.get(0);
                //focused.setFocused(true);
            //}

            // Finally if 'e' was pressed but not captured by a text field, close the overlays/GUI
            if (key == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
                if (!hideOverlays()) {
                    this.mc.thePlayer.closeScreen();
                }
                return;
            }

            // If the key was not captured, let JEI/NEI do its thing
            super.keyTyped(c, key);
        }

        //protected final void setText(CTextField tf, String newText) {
            //String old = tf.getText();
            //tf.setText(newText);
            //onTextFieldChanged(tf, old);
        //}

        //protected void onTextFieldChanged(CTextField tf, String old) {

        //}

        public boolean hideOverlays() {
            for (IGuiOverlay overlay : overlays) {
                if (overlay.isVisible()) {
                    overlay.setIsVisible(false);
                    return true;
                }
            }
            return false;
        }

        @Override
        public void addToolTip(GuiToolTip toolTip) {
            toolTipManager.addToolTip(toolTip);
        }

        @Override
        public void updateScreen() {
            super.updateScreen();

            //for (GuiTextField f : textFields) {
                //f.updateCursorCounter();
            //}
        }

        @Override
        public void handleMouseInput() throws IOException {
            int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
            int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
            int b = Mouse.getEventButton();
            for (IGuiOverlay overlay : overlays) {
                if (overlay != null && overlay.isVisible() && overlay.handleMouseInput(x, y, b)) {
                    return;
                }
            }
            int delta = Mouse.getEventDWheel();
            if (delta != 0) {
                mouseWheel(x, y, delta);
            }
            super.handleMouseInput();
        }

        @Override
        protected boolean isPointInRegion(int p_146978_1_, int p_146978_2_, int p_146978_3_, int p_146978_4_, int p_146978_5_, int p_146978_6_) {
            int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
            int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
            for (IGuiOverlay overlay : overlays) {
                if (overlay != null && overlay.isVisible() && overlay.isMouseInBounds(x, y)) {
                    return false;
                }
            }
            return super.isPointInRegion(p_146978_1_, p_146978_2_, p_146978_3_, p_146978_4_, p_146978_5_, p_146978_6_);
        }


        @Override
        protected void mouseClicked(int x, int y, int button) throws IOException {
            //for (GuiTextField f : textFields) {
                //f.mouseClicked(x, y, button);
            //}
            //if (!scrollbars.isEmpty()) {
                //if (draggingScrollbar != null) {
                    //draggingScrollbar.mouseClicked(x, y, button);
                    //return;
                //}
                //for (VScrollbar vs : scrollbars) {
                    //if (vs.mouseClicked(x, y, button)) {
                        //draggingScrollbar = vs;
                        //return;
                    //}
                //}
            //}
            // Right click field clearing
            //if (button == 1) {
                //for (CTextField tf : textFields) {
                    //if (tf.contains(x, y)) {
                        //setText(tf, "");
                    //}
                //}
            //}
            // Button events for non-left-clicks
            if (button >= 1) {
                for (Object obj : buttonList) {
                    if (obj instanceof IconButton) {
                        IconButton btn = (IconButton) obj;
                        if (btn.mousePressedButton(mc, x, y, button)) {
                            btn.playPressSound(this.mc.getSoundHandler());
                            actionPerformedButton(btn, button);
                        }
                    }
                }
            }
            super.mouseClicked(x, y, button);
        }

        @Override
        protected void mouseReleased(int x, int y, int button) {
            //if (draggingScrollbar != null) {
                //draggingScrollbar.mouseMovedOrUp(x, y, button);
                //draggingScrollbar = null;
            //}
            super.mouseReleased(x, y, button);
        }

        @Override
        protected void mouseClickMove(int x, int y, int button, long time) {
            //if (draggingScrollbar != null) {
                //draggingScrollbar.mouseClickMove(x, y, button, time);
                //return;
            //}
            super.mouseClickMove(x, y, button, time);
        }

        protected void mouseWheel(int x, int y, int delta) {
            //if (!scrollbars.isEmpty()) {
                //for (VScrollbar vs : scrollbars) {
                    //vs.mouseWheel(x, y, delta);
                //}
            //}
        }

        protected void actionPerformedButton(IconButton btn, int mouseButton) throws IOException {
            actionPerformed(btn);
        }

        public void addOverlay(IGuiOverlay overlay) {
            overlays.add(overlay);
        }

        public void removeOverlay(IGuiOverlay overlay) {
            overlays.remove(overlay);
        }

        //public void addScrollbar(VScrollbar vs) {
            //scrollbars.add(vs);
            //vs.adjustPosition();
        //}

        //public void removeScrollbar(VScrollbar vs) {
            //scrollbars.remove(vs);
            //if (draggingScrollbar == vs) {
                //draggingScrollbar = null;
            //}
        //}

        private int realMx, realMy;

        @Override
        protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
            drawForegroundImpl(mouseX, mouseY);

            Timer t = RenderUtil.getTimer();

            if (t != null) {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableDepth();
                zLevel = 300.0F;
                itemRender.zLevel = 300.0F;
                for (IGuiOverlay overlay : overlays) {
                    if (overlay != null && overlay.isVisible()) {
                        overlay.draw(realMx, realMy, t.renderPartialTicks);
                    }
                }
                zLevel = 0F;
                itemRender.zLevel = 0F;
                GlStateManager.enableDepth();
                GlStateManager.popMatrix();
            }
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float par1, int mouseX, int mouseY) {
            //for (GuiTextField f : textFields) {
                //f.drawTextBox();
            //}
            //if (!scrollbars.isEmpty()) {
                //for (VScrollbar vs : scrollbars) {
                    //vs.drawScrollbar(mouseX, mouseY);
                //}
            //}
        }

        @Override
        public void drawScreen(int par1, int par2, float par3) {
            int mx = realMx = par1;
            int my = realMy = par2;

            super.drawScreen(mx, my, par3);

            toolTipManager.drawTooltips(this, par1, par2);
        }

        // Copied from superclass
        protected void drawItemStack(ItemStack stack, int mouseX, int mouseY, String str) {
            if (stack == null) {
                return;
            }

            GlStateManager.translate(0.0F, 0.0F, 32.0F);
            zLevel = 200.0F;
            itemRender.zLevel = 200.0F;
            FontRenderer font = null;
            font = stack.getItem().getFontRenderer(stack);
            if (font == null) {
                font = fontRendererObj;
            }
            itemRender.renderItemIntoGUI(stack, mouseX, mouseY);
            itemRender.renderItemOverlayIntoGUI(font, stack, mouseX, mouseY, str);
            zLevel = 0.0F;
            itemRender.zLevel = 0.0F;
        }

        protected void drawFakeItemsStart() {
            zLevel = 100.0F;
            itemRender.zLevel = 100.0F;

            GlStateManager.enableLighting();
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableDepth();
            RenderHelper.enableGUIStandardItemLighting();
        }

        protected void drawFakeItemStack(int x, int y, ItemStack stack) {
            //itemRender.renderItemIntoGUI(stack, x, y);
            itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        }

        public void drawFakeItemStackStdOverlay(int x, int y, ItemStack stack) {
            itemRender.renderItemOverlayIntoGUI(fontRendererObj, stack, x, y, null);
        }

        protected void drawFakeItemHover(int x, int y) {
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.colorMask(true, true, true, false);
            drawGradientRect(x, y, x + 16, y + 16, 0x80FFFFFF, 0x80FFFFFF);
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.enableDepth();

            GlStateManager.enableLighting();
        }

        protected void drawFakeItemsEnd() {
            itemRender.zLevel = 0.0F;
            zLevel = 0.0F;
        }

        @Override
        public void renderToolTip(ItemStack p_146285_1_, int p_146285_2_, int p_146285_3_) {
            super.renderToolTip(p_146285_1_, p_146285_2_, p_146285_3_);
        }


        public boolean removeToolTip(GuiToolTip toolTip) {
            return toolTipManager.removeToolTip(toolTip);
        }

        protected void drawForegroundImpl(int mouseX, int mouseY) {
            super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        }

        @Override
        public void drawHoveringText(List<String> par1List, int par2, int par3, FontRenderer font) {
            copyOfDrawHoveringText(par1List, par2, par3, font);
        }

        //This is a copy of the super class method due to 'Method not found' errors
        // reported with some mods installed.
        protected void copyOfDrawHoveringText(List<String> par1List, int par2, int par3, FontRenderer font) {

            if (!par1List.isEmpty()) {

                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                int k = 0;
                Iterator<String> iterator = par1List.iterator();

                while (iterator.hasNext()) {
                    String s = iterator.next();
                    int l = font.getStringWidth(s);

                    if (l > k) {
                        k = l;
                    }
                }

                int i1 = par2 + 12;
                int j1 = par3 - 12;
                int k1 = 8;

                if (par1List.size() > 1) {
                    k1 += 2 + (par1List.size() - 1) * 10;
                }

                if (i1 + k > this.width) {
                    i1 -= 28 + k;
                }

                if (j1 + k1 + 6 > this.height) {
                    j1 = this.height - k1 - 6;
                }

                this.zLevel = 300.0F;
                //itemRenderer.zLevel = 300.0F;
                int l1 = -267386864;
                this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
                this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
                this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
                this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
                this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
                int i2 = 1347420415;
                int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
                this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
                this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
                this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
                this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

                for (int k2 = 0; k2 < par1List.size(); ++k2) {
                    String s1 = par1List.get(k2);
                    font.drawStringWithShadow(s1, i1, j1, -1);

                    if (k2 == 0) {
                        j1 += 2;
                    }

                    j1 += 10;
                }

                this.zLevel = 0.0F;
                //itemRenderer.zLevel = 0.0F;
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();

            }
        }

        public float getZlevel() {
            return zLevel;
        }

        @Override
        public int getGuiLeft() {
            return guiLeft;
        }

        @Override
        public int getGuiTop() {
            return guiTop;
        }

        @Override
        public int getXSize() {
            return xSize;
        }

        public int getYSize() {
            return ySize;
        }

        public void setGuiLeft(int i) {
            guiLeft = i;
        }

        public void setGuiTop(int i) {
            guiTop = i;
        }

        public void setXSize(int i) {
            xSize = i;
        }

        public void setYSize(int i) {
            ySize = i;
        }

        @Override
        public FontRenderer getFontRenderer() {
            return Minecraft.getMinecraft().fontRendererObj;
        }

        public void addButton(GuiButton button) {
            if (!buttonList.contains(button)) {
                buttonList.add(button);
            }
        }

        public void removeButton(GuiButton button) {
            buttonList.remove(button);
        }

        public int getOverlayOffsetX() {
            return 0;
        }

        public void doActionPerformed(GuiButton guiButton) throws IOException {
            actionPerformed(guiButton);
        }

        public void clearToolTips() {
        }

        @Override
        public void onGuiClosed() {
            for (IGuiOverlay overlay : overlays) {
                overlay.guiClosed();
            }
        }
}
