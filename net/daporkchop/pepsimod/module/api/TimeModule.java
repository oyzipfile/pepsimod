// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.api;

public abstract class TimeModule extends Module
{
    public long currentMS;
    public long lastMS;
    
    public TimeModule(final String name) {
        super(name);
        this.currentMS = 0L;
        this.lastMS = -1L;
    }
    
    public final void updateMS() {
        this.currentMS = System.currentTimeMillis();
    }
    
    public final void updateLastMS() {
        this.lastMS = System.currentTimeMillis();
    }
    
    public final boolean hasTimePassedM(final long MS) {
        return this.currentMS >= this.lastMS + MS;
    }
    
    public final boolean hasTimePassedS(final float speed) {
        return this.currentMS >= this.lastMS + (long)(1000.0f / speed);
    }
}
