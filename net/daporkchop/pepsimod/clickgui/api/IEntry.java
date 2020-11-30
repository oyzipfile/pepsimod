// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.clickgui.api;

public interface IEntry
{
    boolean isMouseHovered();
    
    void draw(final int p0, final int p1);
    
    void processMouseClick(final int p0, final int p1, final int p2);
    
    void processMouseRelease(final int p0, final int p1, final int p2);
    
    int getX();
    
    void setX(final int p0);
    
    int getY();
    
    void setY(final int p0);
    
    int getWidth();
    
    int getHeight();
    
    int getColor();
    
    boolean shouldRender();
    
    void openGui();
    
    String getName();
    
    boolean isOpen();
    
    void setOpen(final boolean p0);
}
