// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.unsafe.block.offset;

public interface Offsettable
{
    long memoryAddress();
    
    long memorySize();
    
    Object refObj();
    
    default boolean isAbsolute() {
        return this.refObj() == null;
    }
    
    default OffsetData data() {
        return new OffsetData(this.memoryAddress(), this.memorySize());
    }
}
