// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.reference;

import java.lang.ref.SoftReference;

public class LateSoftReference<T> implements Reference<T>
{
    protected SoftReference<T> ref;
    
    public LateSoftReference() {
        this.ref = new SoftReference<T>(null);
    }
    
    @Override
    public T get() {
        return this.ref.get();
    }
    
    @Override
    public T set(final T val) {
        this.ref = new SoftReference<T>(val);
        return val;
    }
}
