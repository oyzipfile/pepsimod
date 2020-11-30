// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.reference;

import lombok.NonNull;

public class LateReference<T> implements Reference<T>
{
    @NonNull
    protected T val;
    
    @Override
    public T get() {
        return this.val;
    }
    
    @Override
    public T set(final T val) {
        return this.val = val;
    }
    
    public LateReference(@NonNull final T val) {
        if (val == null) {
            throw new NullPointerException("val");
        }
        this.val = val;
    }
    
    public LateReference() {
    }
    
    @Override
    public String toString() {
        return "LateReference(val=" + this.val + ")";
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LateReference)) {
            return false;
        }
        final LateReference<?> other = (LateReference<?>)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$val = this.val;
        final Object other$val = other.val;
        if (this$val == null) {
            if (other$val == null) {
                return true;
            }
        }
        else if (this$val.equals(other$val)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof LateReference;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $val = this.val;
        result = result * 59 + (($val == null) ? 43 : $val.hashCode());
        return result;
    }
}
