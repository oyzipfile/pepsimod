// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.function.io;

import java.io.IOException;
import net.daporkchop.lib.common.util.PConstants;
import java.util.function.BiFunction;

@FunctionalInterface
public interface IOBiFunction<T, U, R> extends BiFunction<T, U, R>, PConstants
{
    default R apply(final T t, final U u) {
        try {
            return this.applyThrowing(t, u);
        }
        catch (IOException e) {
            throw this.exception(e);
        }
    }
    
    R applyThrowing(final T p0, final U p1) throws IOException;
}
