// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.function.throwing;

import net.daporkchop.lib.common.util.PConstants;
import java.util.function.BinaryOperator;

@FunctionalInterface
public interface EBinaryOperator<T> extends BinaryOperator<T>, PConstants
{
    default T apply(final T t, final T t2) {
        try {
            return this.applyThrowing(t, t2);
        }
        catch (Exception e) {
            throw this.exception(e);
        }
    }
    
    T applyThrowing(final T p0, final T p1) throws Exception;
}
