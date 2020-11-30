// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.gui.misc;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonTooBeeTooTee extends GuiButton
{
    private ResourceLocation location;
    
    public GuiButtonTooBeeTooTee(final int buttonId, final int x, final int y, final int widthIn, final int heightIn) {
        super(buttonId, x, y, widthIn, heightIn, "");
        this.width = 20;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.x = x;
        this.y = y;
        this.width = widthIn;
        this.height = heightIn;
        this.location = null;
    }
    
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks) {
        if (this.location == null) {
            this.location = new ResourceLocation("pepsimod", "textures/gui/pepsibuttons.png");
        }
        if (this.visible) {
            mc.getTextureManager().bindTexture(this.location);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.hovered = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height);
            final int k = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            this.drawTexturedModalRect(this.x, this.y, 20, this.hovered ? 20 : 0, this.width, this.height);
            this.drawTexturedModalRect(this.x + this.width, this.y, 200 - this.width, k * 20, this.width, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
        }
    }
}
