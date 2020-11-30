// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.unsafe.capability;

import net.daporkchop.lib.unsafe.PUnsafe;
import net.daporkchop.lib.unsafe.PCleaner;
import net.daporkchop.lib.unsafe.util.exception.AlreadyReleasedException;

public interface DirectMemoryHolder extends Releasable
{
    void release() throws AlreadyReleasedException;
    
    public abstract static class AbstractConstantSize implements DirectMemoryHolder
    {
        protected final long pos;
        protected final long size;
        protected final PCleaner cleaner;
        
        public AbstractConstantSize(final long size) {
            this.size = size;
            this.pos = PUnsafe.allocateMemory(size);
            this.cleaner = PCleaner.cleaner(this, this.pos);
        }
        
        @Override
        public void release() throws AlreadyReleasedException {
            synchronized (this.cleaner) {
                if (this.cleaner.isCleaned()) {
                    throw new AlreadyReleasedException();
                }
                this.cleaner.clean();
            }
        }
    }
}
