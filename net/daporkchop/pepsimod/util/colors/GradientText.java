// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.colors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class GradientText extends ColorizedText
{
    public final FixedColorElement[] elements;
    public final int width;
    public String text;
    
    public GradientText(final FixedColorElement[] elements, final int width) {
        this.text = "";
        this.elements = elements;
        this.width = width;
        for (final FixedColorElement element : elements) {
            this.text += element.text;
        }
    }
    
    @Override
    public void drawAtPos(final Gui screen, final int x, final int y) {
        int i = 0;
        for (final FixedColorElement element : this.elements) {
            screen.drawString(Minecraft.getMinecraft().fontRenderer, element.text, x + i, y, element.color);
            i += element.width;
        }
    }
    
    public void drawWithEndAtPos(final Gui screen, final int x, final int y) {
        int i = 0;
        for (final FixedColorElement element : this.elements) {
            i -= element.width;
        }
        for (final FixedColorElement element : this.elements) {
            screen.drawString(Minecraft.getMinecraft().fontRenderer, element.text, x + i, y, element.color);
            i += element.width;
        }
    }
    
    @Override
    public int width() {
        return this.width;
    }
    
    @Override
    public String getRawText() {
        return this.text;
    }
}
