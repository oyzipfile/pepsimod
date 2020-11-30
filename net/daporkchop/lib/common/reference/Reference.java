// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.reference;

import java.util.function.Function;
import lombok.NonNull;
import java.util.function.Supplier;

public interface Reference<T>
{
    T get();
    
    T set(final T p0);
    
    default T swap(final T val) {
        final T old = this.get();
        this.set(val);
        return old;
    }
    
    default boolean missing() {
        return this.get() == null;
    }
    
    default boolean has() {
        return this.get() != null;
    }
    
    default T getOrDefault(final T def) {
        return this.missing() ? def : this.get();
    }
    
    default T getOrSet(final T def) {
        return (T)(this.missing() ? this.set(def) : this.get());
    }
    
    default T compute(@NonNull final Supplier<T> supplier) {
        if (supplier == null) {
            throw new NullPointerException("supplier");
        }
        return this.set(supplier.get());
    }
    
    default T computeIfAbsent(@NonNull final Supplier<T> supplier) {
        if (supplier == null) {
            throw new NullPointerException("supplier");
        }
        return (T)(this.missing() ? this.set(supplier.get()) : this.get());
    }
    
    default T computeIfPresent(@NonNull final Supplier<T> supplier) {
        if (supplier == null) {
            throw new NullPointerException("supplier");
        }
        return (T)(this.has() ? this.set(supplier.get()) : null);
    }
    
    default T map(@NonNull final Function<T, T> function) {
        if (function == null) {
            throw new NullPointerException("function");
        }
        return this.set(function.apply(this.get()));
    }
    
    default T mapIfPresent(@NonNull final Function<T, T> function) {
        if (function == null) {
            throw new NullPointerException("function");
        }
        return (T)(this.has() ? this.set(function.apply(this.get())) : null);
    }
}
