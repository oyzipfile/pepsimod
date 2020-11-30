// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.unsafe.block;

import net.daporkchop.lib.unsafe.util.exception.AlreadyReleasedException;
import net.daporkchop.lib.unsafe.PUnsafe;
import lombok.NonNull;

public class ArrayMemoryBlock implements MemoryBlock
{
    protected Object array;
    protected final long offset;
    protected final long size;
    
    public ArrayMemoryBlock(@NonNull final Object array) {
        if (array == null) {
            throw new NullPointerException("array");
        }
        final Class<?> clazz = array.getClass();
        if (!clazz.isArray()) {
            throw new IllegalArgumentException(String.format("Not an array: %s", clazz.getCanonicalName()));
        }
        if (clazz == byte[].class) {
            this.offset = PUnsafe.ARRAY_BYTE_BASE_OFFSET;
            this.size = ((byte[])array).length;
        }
        else if (clazz == short[].class) {
            this.offset = PUnsafe.ARRAY_SHORT_BASE_OFFSET;
            this.size = ((short[])array).length;
        }
        else if (clazz == int[].class) {
            this.offset = PUnsafe.ARRAY_INT_BASE_OFFSET;
            this.size = ((int[])array).length;
        }
        else if (clazz == long[].class) {
            this.offset = PUnsafe.ARRAY_LONG_BASE_OFFSET;
            this.size = ((long[])array).length;
        }
        else if (clazz == float[].class) {
            this.offset = PUnsafe.ARRAY_FLOAT_BASE_OFFSET;
            this.size = ((float[])array).length;
        }
        else if (clazz == double[].class) {
            this.offset = PUnsafe.ARRAY_DOUBLE_BASE_OFFSET;
            this.size = ((double[])array).length;
        }
        else {
            if (clazz != char[].class) {
                throw new IllegalArgumentException(String.format("Not a primitive array: %s", clazz.getCanonicalName()));
            }
            this.offset = PUnsafe.ARRAY_CHAR_BASE_OFFSET;
            this.size = ((char[])array).length;
        }
        this.array = array;
    }
    
    @Override
    public long memoryAddress() {
        return this.offset;
    }
    
    @Override
    public long memorySize() {
        return this.size;
    }
    
    @Override
    public Object refObj() {
        return this.array;
    }
    
    @Override
    public boolean isAbsolute() {
        return false;
    }
    
    @Override
    public void release() throws AlreadyReleasedException {
        synchronized (this) {
            if (this.array == null) {
                throw new AlreadyReleasedException();
            }
            this.array = null;
        }
    }
    
    @Override
    public byte getByte(final long index) {
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        return PUnsafe.getByte(this.array, this.offset + index);
    }
    
    @Override
    public short getShort(final long index) {
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + 1L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        return PUnsafe.getShort(this.array, this.offset + index);
    }
    
    @Override
    public int getInt(final long index) {
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + 3L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        return PUnsafe.getInt(this.array, this.offset + index);
    }
    
    @Override
    public long getLong(final long index) {
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + 7L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        return PUnsafe.getLong(this.array, this.offset + index);
    }
    
    @Override
    public float getFloat(final long index) {
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + 3L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        return PUnsafe.getFloat(this.array, this.offset + index);
    }
    
    @Override
    public double getDouble(final long index) {
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + 7L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        return PUnsafe.getDouble(this.array, this.offset + index);
    }
    
    @Override
    public char getChar(final long index) {
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + 1L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        return PUnsafe.getChar(this.array, this.offset + index);
    }
    
    @Override
    public void getBytes(final long index, @NonNull final byte[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + len > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(this.array, this.offset, arr, PUnsafe.ARRAY_BYTE_BASE_OFFSET + off, len);
    }
    
    @Override
    public void getShorts(final long index, @NonNull final short[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + (len << 1) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(this.array, this.offset, arr, PUnsafe.ARRAY_SHORT_BASE_OFFSET + (off << 1), len << 1);
    }
    
    @Override
    public void getInts(final long index, @NonNull final int[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + (len << 2) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(this.array, this.offset, arr, PUnsafe.ARRAY_INT_BASE_OFFSET + (off << 2), len << 2);
    }
    
    @Override
    public void getLongs(final long index, @NonNull final long[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + (len << 3) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(this.array, this.offset, arr, PUnsafe.ARRAY_LONG_BASE_OFFSET + (off << 3), len << 3);
    }
    
    @Override
    public void getFloats(final long index, @NonNull final float[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + (len << 2) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(this.array, this.offset, arr, PUnsafe.ARRAY_FLOAT_BASE_OFFSET + (off << 2), len << 2);
    }
    
    @Override
    public void getDoubles(final long index, @NonNull final double[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + (len << 3) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(this.array, this.offset, arr, PUnsafe.ARRAY_DOUBLE_BASE_OFFSET + (off << 3), len << 3);
    }
    
    @Override
    public void getChars(final long index, @NonNull final char[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + (len << 1) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(this.array, this.offset, arr, PUnsafe.ARRAY_CHAR_BASE_OFFSET + (off << 1), len << 1);
    }
    
    @Override
    public void setByte(final long index, final byte val) {
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        PUnsafe.putByte(this.array, this.offset + index, val);
    }
    
    @Override
    public void setShort(final long index, final short val) {
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + 1L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        PUnsafe.putShort(this.array, this.offset + index, val);
    }
    
    @Override
    public void setInt(final long index, final int val) {
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + 3L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        PUnsafe.putInt(this.array, this.offset + index, val);
    }
    
    @Override
    public void setLong(final long index, final long val) {
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + 7L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        PUnsafe.putLong(this.array, this.offset + index, val);
    }
    
    @Override
    public void setFloat(final long index, final float val) {
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + 3L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        PUnsafe.putFloat(this.array, this.offset + index, val);
    }
    
    @Override
    public void setDouble(final long index, final double val) {
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + 7L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        PUnsafe.putDouble(this.array, this.offset + index, val);
    }
    
    @Override
    public void setChar(final long index, final char val) {
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + 1L > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        PUnsafe.putChar(this.array, this.offset + index, val);
    }
    
    @Override
    public void setBytes(final long index, @NonNull final byte[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + len > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(arr, PUnsafe.ARRAY_BYTE_BASE_OFFSET + off, this.array, this.offset, len);
    }
    
    @Override
    public void setShorts(final long index, @NonNull final short[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + (len << 1) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(arr, PUnsafe.ARRAY_SHORT_BASE_OFFSET + (off << 1), this.array, this.offset, len << 1);
    }
    
    @Override
    public void setInts(final long index, @NonNull final int[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + (len << 2) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(arr, PUnsafe.ARRAY_INT_BASE_OFFSET + (off << 2), this.array, this.offset, len << 2);
    }
    
    @Override
    public void setLongs(final long index, @NonNull final long[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + (len << 3) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(arr, PUnsafe.ARRAY_LONG_BASE_OFFSET + (off << 3), this.array, this.offset, len << 3);
    }
    
    @Override
    public void setFloats(final long index, @NonNull final float[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + (len << 2) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(arr, PUnsafe.ARRAY_FLOAT_BASE_OFFSET + (off << 2), this.array, this.offset, len << 2);
    }
    
    @Override
    public void setDoubles(final long index, @NonNull final double[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + (len << 3) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(arr, PUnsafe.ARRAY_DOUBLE_BASE_OFFSET + (off << 3), this.array, this.offset, len << 3);
    }
    
    @Override
    public void setChars(final long index, @NonNull final char[] arr, final int off, final int len) {
        if (arr == null) {
            throw new NullPointerException("arr");
        }
        if (this.array == null) {
            throw new IllegalStateException("Already freed!");
        }
        if (index < 0L || index + (len << 1) > this.size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal index: %d (must be in range 0-%d)", index, this.size));
        }
        if (off < 0 || off + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("Illegal offset/length: off=%d,length=%d for array length %d", off, len, arr.length));
        }
        PUnsafe.copyMemory(arr, PUnsafe.ARRAY_CHAR_BASE_OFFSET + (off << 1), this.array, this.offset, len << 1);
    }
}
