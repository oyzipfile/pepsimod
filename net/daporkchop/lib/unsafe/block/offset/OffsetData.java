// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.unsafe.block.offset;

public class OffsetData
{
    protected final long offset;
    protected final long length;
    
    public OffsetData(final long offset, final long length) {
        this.offset = offset;
        this.length = length;
    }
    
    public long getOffset() {
        return this.offset;
    }
    
    public long getLength() {
        return this.length;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OffsetData)) {
            return false;
        }
        final OffsetData other = (OffsetData)o;
        return other.canEqual(this) && this.getOffset() == other.getOffset() && this.getLength() == other.getLength();
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof OffsetData;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $offset = this.getOffset();
        result = result * 59 + (int)($offset >>> 32 ^ $offset);
        final long $length = this.getLength();
        result = result * 59 + (int)($length >>> 32 ^ $length);
        return result;
    }
    
    @Override
    public String toString() {
        return "OffsetData(offset=" + this.getOffset() + ", length=" + this.getLength() + ")";
    }
}
