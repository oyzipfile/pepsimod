// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util;

import net.minecraft.util.math.MathHelper;

public class BetterScaledResolution extends PepsiConstants
{
    public static BetterScaledResolution INSTANCE;
    public int scaledWidth;
    public int scaledHeight;
    public int scaleFactor;
    
    public BetterScaledResolution() {
        this.update();
        BetterScaledResolution.INSTANCE = this;
    }
    
    public void update() {
        this.scaledWidth = BetterScaledResolution.mc.displayWidth;
        this.scaledHeight = BetterScaledResolution.mc.displayHeight;
        this.scaleFactor = 1;
        final boolean flag = BetterScaledResolution.mc.isUnicode();
        int i = BetterScaledResolution.mc.gameSettings.guiScale;
        if (i == 0) {
            i = 1000;
        }
        while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240) {
            ++this.scaleFactor;
        }
        if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
            --this.scaleFactor;
        }
        final double scaledWidthD = this.scaledWidth / (double)this.scaleFactor;
        final double scaledHeightD = this.scaledHeight / (double)this.scaleFactor;
        this.scaledWidth = MathHelper.ceil(scaledWidthD);
        this.scaledHeight = MathHelper.ceil(scaledHeightD);
    }
}
