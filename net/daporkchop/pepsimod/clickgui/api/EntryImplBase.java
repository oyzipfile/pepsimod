// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.clickgui.api;

import net.daporkchop.pepsimod.util.PepsiConstants;

public abstract class EntryImplBase extends PepsiConstants implements IEntry
{
    public final int width;
    public final int height;
    public int x;
    public int y;
    protected boolean isHoveredCached;
    
    public static void drawString(final int x, final int y, final String text, final int color) {
        EntryImplBase.mc.fontRenderer.drawString(text, (float)x, (float)y, color, false);
    }
    
    public EntryImplBase(final int x, final int y, final int width, final int height) {
        this.isHoveredCached = false;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    @Override
    public boolean isMouseHovered() {
        return this.isHoveredCached;
    }
    
    protected void updateIsMouseHovered(final int mouseX, final int mouseY) {
        final int x = this.getX();
        final int y = this.getY();
        final int maxX = x + this.width;
        final int maxY = y + this.height;
        this.isHoveredCached = (x <= mouseX && mouseX <= maxX && y <= mouseY && mouseY <= maxY);
    }
}
