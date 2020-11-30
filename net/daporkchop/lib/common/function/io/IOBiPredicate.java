// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.function.io;

import java.io.IOException;
import net.daporkchop.lib.common.util.PConstants;
import java.util.function.BiPredicate;

@FunctionalInterface
public interface IOBiPredicate<T, U> extends BiPredicate<T, U>, PConstants
{
    default boolean test(final T t, final U u) {
        try {
            return this.testThrowing(t, u);
        }
        catch (IOException e) {
            throw this.exception(e);
        }
    }
    
    boolean testThrowing(final T p0, final U p1) throws IOException;
}
