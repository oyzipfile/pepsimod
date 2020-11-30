// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.colors;

import net.minecraft.client.gui.Gui;
import net.daporkchop.pepsimod.util.PepsiConstants;

public abstract class ColorizedText extends PepsiConstants
{
    public abstract int width();
    
    public abstract void drawAtPos(final Gui p0, final int p1, final int p2);
    
    public abstract String getRawText();
}
