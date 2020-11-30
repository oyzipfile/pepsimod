// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.function.throwing;

import net.daporkchop.lib.common.util.PConstants;
import java.util.function.UnaryOperator;

@FunctionalInterface
public interface EUnaryOperator<T> extends UnaryOperator<T>, PConstants
{
    default T apply(final T t) {
        try {
            return this.applyThrowing(t);
        }
        catch (Exception e) {
            throw this.exception(e);
        }
    }
    
    T applyThrowing(final T p0) throws Exception;
}
