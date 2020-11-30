// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.util;

import net.daporkchop.lib.unsafe.PUnsafe;
import lombok.NonNull;

@Deprecated
public interface PConstants
{
    default RuntimeException p_exception(@NonNull final Throwable t) {
        if (t == null) {
            throw new NullPointerException("t");
        }
        PUnsafe.throwException(t);
        return new RuntimeException(t);
    }
    
    default Object getNull() {
        return null;
    }
    
    default RuntimeException exception(@NonNull final Throwable t) {
        if (t == null) {
            throw new NullPointerException("t");
        }
        return p_exception(t);
    }
}
