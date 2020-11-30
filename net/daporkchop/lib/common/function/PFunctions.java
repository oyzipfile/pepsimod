// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.function;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.lang.reflect.Constructor;
import net.daporkchop.lib.common.util.PConstants;
import java.util.function.Function;
import lombok.NonNull;

public abstract class PFunctions
{
    public static <T, R> Function<T, R> throwing(@NonNull final Class<? extends Throwable> clazz) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        try {
            final Constructor<? extends Throwable> constructor = clazz.getDeclaredConstructor((Class<?>[])new Class[0]);
            constructor.setAccessible(true);
            return throwing(() -> (Throwable)constructor.newInstance(new Object[0]));
        }
        catch (NoSuchMethodException e) {
            throw PConstants.p_exception(e);
        }
    }
    
    public static <T, R> Function<T, R> throwing(@NonNull final Supplier<Throwable> supplier) {
        if (supplier == null) {
            throw new NullPointerException("supplier");
        }
        return t -> {
            throw PConstants.p_exception(supplier.get());
        };
    }
    
    public static <T> Predicate<T> invert(@NonNull final Predicate<T> predicate) {
        if (predicate == null) {
            throw new NullPointerException("predicate");
        }
        return t -> !predicate.test(t);
    }
    
    private PFunctions() {
    }
}
