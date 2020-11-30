// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.colors;

import net.minecraft.client.Minecraft;

public class FixedColorElement extends ColorizedElement
{
    public final int color;
    
    public FixedColorElement(final int color, final String text) {
        this.color = color;
        this.text = text;
        this.width = Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
    }
}
