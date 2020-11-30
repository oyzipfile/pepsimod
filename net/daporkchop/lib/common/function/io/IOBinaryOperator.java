// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.function.io;

import java.io.IOException;
import net.daporkchop.lib.common.util.PConstants;
import java.util.function.BinaryOperator;

@FunctionalInterface
public interface IOBinaryOperator<T> extends BinaryOperator<T>, PConstants
{
    default T apply(final T t, final T t2) {
        try {
            return this.applyThrowing(t, t2);
        }
        catch (IOException e) {
            throw this.exception(e);
        }
    }
    
    T applyThrowing(final T p0, final T p1) throws IOException;
}
