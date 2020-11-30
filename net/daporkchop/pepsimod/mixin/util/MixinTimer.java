// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.util;

import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.client.Minecraft;
import net.daporkchop.pepsimod.module.impl.misc.TimerMod;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Timer.class })
public abstract class MixinTimer
{
    @Shadow
    public int elapsedTicks;
    @Shadow
    public float renderPartialTicks;
    @Shadow
    public float elapsedPartialTicks;
    @Shadow
    private long lastSyncSysClock;
    @Shadow
    private float tickLength;
    
    @Overwrite
    public void updateTimer() {
        final float timerSpeed = (TimerMod.INSTANCE == null) ? 1.0f : TimerMod.INSTANCE.getMultiplier();
        final long i = Minecraft.getSystemTime();
        this.elapsedPartialTicks = (i - this.lastSyncSysClock) / this.tickLength * timerSpeed;
        this.lastSyncSysClock = i;
        this.renderPartialTicks += this.elapsedPartialTicks;
        this.elapsedTicks = (int)this.renderPartialTicks;
        this.renderPartialTicks -= this.elapsedTicks;
    }
}
