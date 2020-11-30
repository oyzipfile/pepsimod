// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.function.throwing;

import net.daporkchop.lib.common.util.PConstants;
import java.util.function.Predicate;

@FunctionalInterface
public interface EPredicate<T> extends Predicate<T>, PConstants
{
    default boolean test(final T t) {
        try {
            return this.testThrowing(t);
        }
        catch (Exception e) {
            throw this.exception(e);
        }
    }
    
    boolean testThrowing(final T p0) throws Exception;
}
