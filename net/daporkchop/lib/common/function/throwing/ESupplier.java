// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.function.throwing;

import net.daporkchop.lib.common.util.PConstants;
import java.util.function.Supplier;

@FunctionalInterface
public interface ESupplier<T> extends Supplier<T>, PConstants
{
    default T get() {
        try {
            return this.getThrowing();
        }
        catch (Exception e) {
            throw this.exception(e);
        }
    }
    
    T getThrowing() throws Exception;
}
