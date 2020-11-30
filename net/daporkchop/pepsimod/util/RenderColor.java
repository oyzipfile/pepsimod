// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util;

import org.lwjgl.opengl.GL11;

public class RenderColor
{
    public byte r;
    public byte g;
    public byte b;
    public byte a;
    public int rOrig;
    public int gOrig;
    public int bOrig;
    public int aOrig;
    
    public static void glColor(final int r, final int g, final int b) {
        glColor(r, g, b, 255);
    }
    
    public static void glColor(final int r, final int g, final int b, final int a) {
        GL11.glColor4b((byte)Math.floorDiv(r, 2), (byte)Math.floorDiv(g, 2), (byte)Math.floorDiv(b, 2), (byte)Math.floorDiv(a, 2));
    }
    
    public RenderColor(final int r, final int g, final int b, final int a) {
        this.r = (byte)Math.floorDiv(r, 2);
        this.g = (byte)Math.floorDiv(g, 2);
        this.b = (byte)Math.floorDiv(b, 2);
        this.a = (byte)Math.floorDiv(a, 2);
        this.rOrig = r;
        this.gOrig = g;
        this.bOrig = b;
        this.aOrig = a;
    }
    
    public int getIntColor() {
        return (this.a & 0xFF) << 24 | (this.r & 0xFF) << 16 | (this.g & 0xFF) << 8 | (this.b & 0xFF);
    }
}
