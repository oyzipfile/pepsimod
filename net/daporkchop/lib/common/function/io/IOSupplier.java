// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.function.io;

import java.io.IOException;
import net.daporkchop.lib.common.util.PConstants;
import java.util.function.Supplier;

@FunctionalInterface
public interface IOSupplier<T> extends Supplier<T>, PConstants
{
    default T get() {
        try {
            return this.getThrowing();
        }
        catch (IOException e) {
            throw this.exception(e);
        }
    }
    
    T getThrowing() throws IOException;
}
