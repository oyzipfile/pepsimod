// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.unsafe.capability;

import net.daporkchop.lib.unsafe.util.exception.AlreadyReleasedException;

public interface AccessibleDirectMemoryHolder extends DirectMemoryHolder
{
    long memoryAddress() throws AlreadyReleasedException;
    
    long memorySize();
}
