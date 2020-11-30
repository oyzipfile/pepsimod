// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.unsafe.block;

import net.daporkchop.lib.unsafe.PUnsafe;
import lombok.NonNull;
import net.daporkchop.lib.unsafe.block.offset.Offsettable;
import net.daporkchop.lib.unsafe.capability.AccessibleDirectMemoryHolder;

public interface MemoryBlock extends AccessibleDirectMemoryHolder, Offsettable
{
    default MemoryBlock direct(final long size) {
        return new DirectMemoryBlock(size);
    }
    
    default MemoryBlock wrap(@NonNull final byte[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        return new ArrayMemoryBlock(arr);
    }
    
    default MemoryBlock wrap(@NonNull final short[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        return new ArrayMemoryBlock(arr);
    }
    
    default MemoryBlock wrap(@NonNull final int[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        return new ArrayMemoryBlock(arr);
    }
    
    default MemoryBlock wrap(@NonNull final long[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        return new ArrayMemoryBlock(arr);
    }
    
    default MemoryBlock wrap(@NonNull final float[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        return new ArrayMemoryBlock(arr);
    }
    
    default MemoryBlock wrap(@NonNull final double[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        return new ArrayMemoryBlock(arr);
    }
    
    default MemoryBlock wrap(@NonNull final char[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        return new ArrayMemoryBlock(arr);
    }
    
    default long size() {
        return this.memorySize();
    }
    
    byte getByte(final long p0);
    
    short getShort(final long p0);
    
    int getInt(final long p0);
    
    long getLong(final long p0);
    
    float getFloat(final long p0);
    
    double getDouble(final long p0);
    
    char getChar(final long p0);
    
    default void getBytes(final long index, @NonNull final byte[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        this.getBytes(index, arr, 0, arr.length);
    }
    
    void getBytes(final long p0, @NonNull final byte[] p1, final int p2, final int p3);
    
    default void getShorts(final long index, @NonNull final short[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        this.getShorts(index, arr, 0, arr.length);
    }
    
    void getShorts(final long p0, @NonNull final short[] p1, final int p2, final int p3);
    
    default void getInts(final long index, @NonNull final int[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        this.getInts(index, arr, 0, arr.length);
    }
    
    void getInts(final long p0, @NonNull final int[] p1, final int p2, final int p3);
    
    default void getLongs(final long index, @NonNull final long[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        this.getLongs(index, arr, 0, arr.length);
    }
    
    void getLongs(final long p0, @NonNull final long[] p1, final int p2, final int p3);
    
    default void getFloats(final long index, @NonNull final float[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        this.getFloats(index, arr, 0, arr.length);
    }
    
    void getFloats(final long p0, @NonNull final float[] p1, final int p2, final int p3);
    
    default void getDoubles(final long index, @NonNull final double[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        this.getDoubles(index, arr, 0, arr.length);
    }
    
    void getDoubles(final long p0, @NonNull final double[] p1, final int p2, final int p3);
    
    default void getChars(final long index, @NonNull final char[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        this.getChars(index, arr, 0, arr.length);
    }
    
    void getChars(final long p0, @NonNull final char[] p1, final int p2, final int p3);
    
    void setByte(final long p0, final byte p1);
    
    void setShort(final long p0, final short p1);
    
    void setInt(final long p0, final int p1);
    
    void setLong(final long p0, final long p1);
    
    void setFloat(final long p0, final float p1);
    
    void setDouble(final long p0, final double p1);
    
    void setChar(final long p0, final char p1);
    
    default void setBytes(final long index, @NonNull final byte[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        this.setBytes(index, arr, 0, arr.length);
    }
    
    void setBytes(final long p0, @NonNull final byte[] p1, final int p2, final int p3);
    
    default void setShorts(final long index, @NonNull final short[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        this.setShorts(index, arr, 0, arr.length);
    }
    
    void setShorts(final long p0, @NonNull final short[] p1, final int p2, final int p3);
    
    default void setInts(final long index, @NonNull final int[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        this.setInts(index, arr, 0, arr.length);
    }
    
    void setInts(final long p0, @NonNull final int[] p1, final int p2, final int p3);
    
    default void setLongs(final long index, @NonNull final long[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        this.setLongs(index, arr, 0, arr.length);
    }
    
    void setLongs(final long p0, @NonNull final long[] p1, final int p2, final int p3);
    
    default void setFloats(final long index, @NonNull final float[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        this.setFloats(index, arr, 0, arr.length);
    }
    
    void setFloats(final long p0, @NonNull final float[] p1, final int p2, final int p3);
    
    default void setDoubles(final long index, @NonNull final double[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        this.setDoubles(index, arr, 0, arr.length);
    }
    
    void setDoubles(final long p0, @NonNull final double[] p1, final int p2, final int p3);
    
    default void setChars(final long index, @NonNull final char[] arr) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        this.setChars(index, arr, 0, arr.length);
    }
    
    void setChars(final long p0, @NonNull final char[] p1, final int p2, final int p3);
    
    default void clear() {
        if (this.isAbsolute()) {
            PUnsafe.setMemory(this.memoryAddress(), this.memorySize(), (byte)0);
        }
        else {
            PUnsafe.setMemory(this.refObj(), this.memoryAddress(), this.memorySize(), (byte)0);
        }
    }
}
