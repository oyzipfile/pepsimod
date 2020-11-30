// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.function.throwing;

import net.daporkchop.lib.common.util.PConstants;
import java.util.function.Function;

@FunctionalInterface
public interface EFunction<T, R> extends Function<T, R>, PConstants
{
    default R apply(final T t) {
        try {
            return this.applyThrowing(t);
        }
        catch (Exception e) {
            throw this.exception(e);
        }
    }
    
    R applyThrowing(final T p0) throws Exception;
}
