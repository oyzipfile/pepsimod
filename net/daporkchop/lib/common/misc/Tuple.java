// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.misc;

import net.daporkchop.lib.unsafe.PUnsafe;

public class Tuple<A, B>
{
    protected static final long A_OFFSET;
    protected static final long B_OFFSET;
    protected A a;
    protected B b;
    
    public boolean isANull() {
        return this.a == null;
    }
    
    public boolean isANonNull() {
        return this.a != null;
    }
    
    public boolean isBNull() {
        return this.b == null;
    }
    
    public boolean isBNonNull() {
        return this.b != null;
    }
    
    public Tuple<A, B> atomicSetA(final A a) {
        PUnsafe.putObjectVolatile(this, Tuple.A_OFFSET, a);
        return this;
    }
    
    public Tuple<A, B> atomicSetB(final B b) {
        PUnsafe.putObjectVolatile(this, Tuple.B_OFFSET, b);
        return this;
    }
    
    public A atomicSwapA(final A a) {
        return PUnsafe.pork_swapObject(this, Tuple.A_OFFSET, a);
    }
    
    public B atomicSwapB(final B b) {
        return PUnsafe.pork_swapObject(this, Tuple.B_OFFSET, b);
    }
    
    public Tuple() {
    }
    
    public Tuple(final A a, final B b) {
        this.a = a;
        this.b = b;
    }
    
    public A getA() {
        return this.a;
    }
    
    public B getB() {
        return this.b;
    }
    
    public Tuple<A, B> setA(final A a) {
        this.a = a;
        return this;
    }
    
    public Tuple<A, B> setB(final B b) {
        this.b = b;
        return this;
    }
    
    @Override
    public String toString() {
        return "Tuple(a=" + this.getA() + ", b=" + this.getB() + ")";
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Tuple)) {
            return false;
        }
        final Tuple<?, ?> other = (Tuple<?, ?>)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$a = this.getA();
        final Object other$a = other.getA();
        Label_0065: {
            if (this$a == null) {
                if (other$a == null) {
                    break Label_0065;
                }
            }
            else if (this$a.equals(other$a)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$b = this.getB();
        final Object other$b = other.getB();
        if (this$b == null) {
            if (other$b == null) {
                return true;
            }
        }
        else if (this$b.equals(other$b)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof Tuple;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $a = this.getA();
        result = result * 59 + (($a == null) ? 43 : $a.hashCode());
        final Object $b = this.getB();
        result = result * 59 + (($b == null) ? 43 : $b.hashCode());
        return result;
    }
    
    static {
        A_OFFSET = PUnsafe.pork_getOffset(Tuple.class, "a");
        B_OFFSET = PUnsafe.pork_getOffset(Tuple.class, "b");
    }
}
