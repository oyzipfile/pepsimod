// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.function.throwing;

import net.daporkchop.lib.common.util.PConstants;

@FunctionalInterface
public interface ERunnable extends Runnable, PConstants
{
    default void run() {
        try {
            this.runThrowing();
        }
        catch (Exception e) {
            throw this.exception(e);
        }
    }
    
    void runThrowing() throws Exception;
}
