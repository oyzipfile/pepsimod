// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.function.throwing;

import net.daporkchop.lib.common.util.PConstants;
import java.util.function.Consumer;

@FunctionalInterface
public interface EConsumer<T> extends Consumer<T>, PConstants
{
    default void accept(final T t) {
        try {
            this.acceptThrowing(t);
        }
        catch (Exception e) {
            throw this.exception(e);
        }
    }
    
    void acceptThrowing(final T p0) throws Exception;
}
