// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.optimization;

public interface OverrideCounter
{
    void incrementOverride();
    
    void decrementOverride();
    
    int getOverride();
    
    default boolean isOverriden() {
        return this.getOverride() > 0;
    }
}
