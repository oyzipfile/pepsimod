// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.unsafe.block;

import lombok.NonNull;
import net.daporkchop.lib.unsafe.PUnsafe;
import net.daporkchop.lib.unsafe.capability.DirectMemoryHolder;

public class DirectMemoryBlock extends DirectMemoryHolder.AbstractConstantSize implements MemoryBlock
{
    public DirectMemoryBlock(final long size) {
        super(size);
    }
    
    @Override
    public long memoryAddress() {
        return this.pos;
    }
    
    @Override
    public long memorySize() {
        return this.size;
    }
    
    @Override
    public Object refObj() {
        return null;
    }
    
    @Override
    public boolean isAbsolute() {
        return true;
    }
    
    @Override
    public byte getByte(final long index) {
        if (index < 0L || index > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        return PUnsafe.getByte(this.pos + index);
    }
    
    @Override
    public short getShort(final long index) {
        if (index < 0L || index + 1L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        return PUnsafe.getShort(this.pos + index);
    }
    
    @Override
    public int getInt(final long index) {
        if (index < 0L || index + 3L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        return PUnsafe.getInt(this.pos + index);
    }
    
    @Override
    public long getLong(final long index) {
        if (index < 0L || index + 7L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        return PUnsafe.getLong(this.pos + index);
    }
    
    @Override
    public float getFloat(final long index) {
        if (index < 0L || index + 3L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        return PUnsafe.getFloat(this.pos + index);
    }
    
    @Override
    public double getDouble(final long index) {
        if (index < 0L || index + 7L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        return PUnsafe.getDouble(this.pos + index);
    }
    
    @Override
    public char getChar(final long index) {
        if (index < 0L || index + 1L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        return PUnsafe.getChar(this.pos + index);
    }
    
    @Override
    public void getBytes(final long index, @NonNull final byte[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (index < 0L || index + len > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(null, this.pos, arr, PUnsafe.ARRAY_BYTE_BASE_OFFSET + off, len);
    }
    
    @Override
    public void getShorts(final long index, @NonNull final short[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (index < 0L || index + (len << 1) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(null, this.pos, arr, PUnsafe.ARRAY_SHORT_BASE_OFFSET + (off << 1), len << 1);
    }
    
    @Override
    public void getInts(final long index, @NonNull final int[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (index < 0L || index + (len << 2) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(null, this.pos, arr, PUnsafe.ARRAY_INT_BASE_OFFSET + (off << 2), len << 2);
    }
    
    @Override
    public void getLongs(final long index, @NonNull final long[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (index < 0L || index + (len << 3) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(null, this.pos, arr, PUnsafe.ARRAY_LONG_BASE_OFFSET + (off << 3), len << 3);
    }
    
    @Override
    public void getFloats(final long index, @NonNull final float[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (index < 0L || index + (len << 2) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(null, this.pos, arr, PUnsafe.ARRAY_FLOAT_BASE_OFFSET + (off << 2), len << 2);
    }
    
    @Override
    public void getDoubles(final long index, @NonNull final double[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (index < 0L || index + (len << 3) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(null, this.pos, arr, PUnsafe.ARRAY_DOUBLE_BASE_OFFSET + (off << 3), len << 3);
    }
    
    @Override
    public void getChars(final long index, @NonNull final char[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (index < 0L || index + (len << 1) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(null, this.pos, arr, PUnsafe.ARRAY_CHAR_BASE_OFFSET + (off << 1), len << 1);
    }
    
    @Override
    public void setByte(final long index, final byte val) {
        if (index < 0L || index > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        PUnsafe.putByte(this.pos + index, val);
    }
    
    @Override
    public void setShort(final long index, final short val) {
        if (index < 0L || index + 1L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        PUnsafe.putShort(this.pos + index, val);
    }
    
    @Override
    public void setInt(final long index, final int val) {
        if (index < 0L || index + 3L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        PUnsafe.putInt(this.pos + index, val);
    }
    
    @Override
    public void setLong(final long index, final long val) {
        if (index < 0L || index + 7L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        PUnsafe.putLong(this.pos + index, val);
    }
    
    @Override
    public void setFloat(final long index, final float val) {
        if (index < 0L || index + 3L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        PUnsafe.putFloat(this.pos + index, val);
    }
    
    @Override
    public void setDouble(final long index, final double val) {
        if (index < 0L || index + 7L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        PUnsafe.putDouble(this.pos + index, val);
    }
    
    @Override
    public void setChar(final long index, final char val) {
        if (index < 0L || index + 1L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        PUnsafe.putChar(this.pos + index, val);
    }
    
    @Override
    public void setBytes(final long index, @NonNull final byte[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (index < 0L || index + len > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(arr, PUnsafe.ARRAY_BYTE_BASE_OFFSET + off, null, this.pos, len);
    }
    
    @Override
    public void setShorts(final long index, @NonNull final short[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (index < 0L || index + (len << 1) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(arr, PUnsafe.ARRAY_SHORT_BASE_OFFSET + (off << 1), null, this.pos, len << 1);
    }
    
    @Override
    public void setInts(final long index, @NonNull final int[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (index < 0L || index + (len << 2) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(arr, PUnsafe.ARRAY_INT_BASE_OFFSET + (off << 2), null, this.pos, len << 2);
    }
    
    @Override
    public void setLongs(final long index, @NonNull final long[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (index < 0L || index + (len << 3) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(arr, PUnsafe.ARRAY_LONG_BASE_OFFSET + (off << 3), null, this.pos, len << 3);
    }
    
    @Override
    public void setFloats(final long index, @NonNull final float[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (index < 0L || index + (len << 2) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(arr, PUnsafe.ARRAY_FLOAT_BASE_OFFSET + (off << 2), null, this.pos, len << 2);
    }
    
    @Override
    public void setDoubles(final long index, @NonNull final double[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (index < 0L || index + (len << 3) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(arr, PUnsafe.ARRAY_DOUBLE_BASE_OFFSET + (off << 3), null, this.pos, len << 3);
    }
    
    @Override
    public void setChars(final long index, @NonNull final char[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (index < 0L || index + (len << 1) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(arr, PUnsafe.ARRAY_CHAR_BASE_OFFSET + (off << 1), null, this.pos, len << 1);
    }
    
    @Override
    public String toString() {
        return "DirectMemoryBlock()";
    }
}
