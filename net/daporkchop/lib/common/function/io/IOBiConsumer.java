// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.function.io;

import java.io.IOException;
import net.daporkchop.lib.common.util.PConstants;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface IOBiConsumer<T, U> extends BiConsumer<T, U>, PConstants
{
    default void accept(final T t, final U u) {
        try {
            this.acceptThrowing(t, u);
        }
        catch (IOException e) {
            throw this.exception(e);
        }
    }
    
    void acceptThrowing(final T p0, final U p1) throws IOException;
}
