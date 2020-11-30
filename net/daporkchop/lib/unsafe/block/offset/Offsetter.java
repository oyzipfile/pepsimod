// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.unsafe.block.offset;

import java.util.function.Function;
import lombok.NonNull;
import java.util.function.ToLongFunction;

public interface Offsetter<T>
{
    default <T> Offsetter<T> of(@NonNull final ToLongFunction<T> offset, @NonNull final ToLongFunction<T> length) {
        if (offset == null) {
            throw new NullPointerException("offset");
        }
        if (length == null) {
            throw new NullPointerException("length");
        }
        return of(offset, length, null);
    }
    
    default <T> Offsetter<T> of(@NonNull final ToLongFunction<T> offset, @NonNull final ToLongFunction<T> length, final Function<T, Object> ref) {
        if (offset == null) {
            throw new NullPointerException("offset");
        }
        if (length == null) {
            throw new NullPointerException("length");
        }
        return new Offsetter<T>() {
            @Override
            public long memoryOffset(@NonNull final T val) {
                if (val == null) {
                    throw new NullPointerException("val");
                }
                return offset.applyAsLong(val);
            }
            
            @Override
            public long memoryLength(@NonNull final T val) {
                if (val == null) {
                    throw new NullPointerException("val");
                }
                return length.applyAsLong(val);
            }
            
            @Override
            public Object refObj(@NonNull final T val) {
                if (val == null) {
                    throw new NullPointerException("val");
                }
                return (ref == null) ? null : ref.apply(val);
            }
            
            @Override
            public boolean isAbsolute() {
                return ref == null;
            }
        };
    }
    
    long memoryOffset(@NonNull final T p0);
    
    long memoryLength(@NonNull final T p0);
    
    Object refObj(@NonNull final T p0);
    
    boolean isAbsolute();
    
    default OffsetData data(@NonNull final T val) {
        if (val == null) {
            throw new NullPointerException("val");
        }
        return new OffsetData(this.memoryOffset(val), this.memoryLength(val));
    }
}
