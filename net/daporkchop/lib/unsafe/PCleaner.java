// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.unsafe;

import java.util.concurrent.atomic.AtomicLong;
import lombok.NonNull;
import sun.misc.Cleaner;

public final class PCleaner
{
    protected static final long CLEANER_NEXT_OFFSET;
    protected static final long CLEANER_THUNK_OFFSET;
    protected static final Runnable NULL_RUNNABLE;
    @NonNull
    protected final Cleaner delegate;
    
    public static PCleaner cleaner(@NonNull final Object o, @NonNull final Runnable cleaner) {
        if (o == null) {
            throw new NullPointerException("o");
        }
        if (cleaner == null) {
            throw new NullPointerException("cleaner");
        }
        return new PCleaner(Cleaner.create(o, cleaner));
    }
    
    public static PCleaner cleaner(@NonNull final Object o, final long addr) {
        if (o == null) {
            throw new NullPointerException("o");
        }
        return new PCleaner(Cleaner.create(o, () -> PUnsafe.freeMemory(addr)));
    }
    
    public static PCleaner cleaner(@NonNull final Object o, @NonNull final AtomicLong addrRef) {
        if (o == null) {
            throw new NullPointerException("o");
        }
        if (addrRef == null) {
            throw new NullPointerException("addrRef");
        }
        final long addr;
        return new PCleaner(Cleaner.create(o, () -> {
            addr = addrRef.getAndSet(-1L);
            if (addr != -1L) {
                PUnsafe.freeMemory(addr);
            }
        }));
    }
    
    public void clean() {
        this.delegate.clean();
    }
    
    public boolean isCleaned() {
        return PUnsafe.getObject(this.delegate, PCleaner.CLEANER_NEXT_OFFSET) == this.delegate;
    }
    
    public synchronized void setCleanTask(@NonNull final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("runnable");
        }
        PUnsafe.putObjectVolatile(this.delegate, PCleaner.CLEANER_THUNK_OFFSET, runnable);
    }
    
    public synchronized void disable() {
        PUnsafe.putObjectVolatile(this.delegate, PCleaner.CLEANER_THUNK_OFFSET, PCleaner.NULL_RUNNABLE);
    }
    
    public synchronized void invalidate() {
        this.disable();
        this.clean();
    }
    
    public PCleaner(@NonNull final Cleaner delegate) {
        if (delegate == null) {
            throw new NullPointerException("delegate");
        }
        this.delegate = delegate;
    }
    
    @NonNull
    public Cleaner getDelegate() {
        return this.delegate;
    }
    
    static {
        CLEANER_NEXT_OFFSET = PUnsafe.pork_getOffset(Cleaner.class, "next");
        CLEANER_THUNK_OFFSET = PUnsafe.pork_getOffset(Cleaner.class, "thunk");
        NULL_RUNNABLE = (() -> {});
    }
}
