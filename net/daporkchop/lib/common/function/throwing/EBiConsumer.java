// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.function.throwing;

import net.daporkchop.lib.common.util.PConstants;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface EBiConsumer<T, U> extends BiConsumer<T, U>, PConstants
{
    default void accept(final T t, final U u) {
        try {
            this.acceptThrowing(t, u);
        }
        catch (Exception e) {
            throw this.exception(e);
        }
    }
    
    void acceptThrowing(final T p0, final U p1) throws Exception;
}
