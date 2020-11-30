// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.colors;

import net.daporkchop.pepsimod.util.PepsiConstants;

public class PlainColorElement extends ColorizedElement
{
    public PlainColorElement(final String text) {
        this.text = text;
        this.width = PepsiConstants.mc.fontRenderer.getStringWidth(text);
    }
}
