// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.api;

public enum ModuleSortType
{
    ALPHABETICAL, 
    SIZE, 
    RANDOM, 
    DEFAULT;
    
    public static ModuleSortType fromOrdinal(final int ordinal) {
        return values()[ordinal];
    }
}
