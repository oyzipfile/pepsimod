// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.function;

import java.util.function.Supplier;

public interface ThrowingSupplier<T> extends Supplier<T>
{
    default T get() {
        try {
            return this.getThrowing();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    T getThrowing() throws Exception;
}
