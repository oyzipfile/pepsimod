// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.unsafe.block.offset;

import java.util.IdentityHashMap;
import java.nio.Buffer;
import net.daporkchop.lib.unsafe.PUnsafe;
import java.nio.ByteBuffer;
import lombok.NonNull;
import java.util.Map;

public class OffsetLookup
{
    public static final OffsetLookup INSTANCE;
    @NonNull
    protected final Map<Class<?>, Offsetter> offsetters;
    
    public OffsetLookup addDefault() {
        final long byteBuffer_hb_offset = PUnsafe.pork_getOffset(ByteBuffer.class, "hb");
        final long buffer_address_offset = PUnsafe.pork_getOffset(Buffer.class, "address");
        return this.add(boolean[].class, (Offsetter<boolean[]>)Offsetter.of(v -> PUnsafe.ARRAY_BOOLEAN_BASE_OFFSET, v -> v.length * (long)PUnsafe.ARRAY_BOOLEAN_INDEX_SCALE)).add(byte[].class, (Offsetter<byte[]>)Offsetter.of(v -> PUnsafe.ARRAY_BYTE_BASE_OFFSET, v -> v.length * (long)PUnsafe.ARRAY_BYTE_INDEX_SCALE)).add(short[].class, (Offsetter<short[]>)Offsetter.of(v -> PUnsafe.ARRAY_SHORT_BASE_OFFSET, v -> v.length * (long)PUnsafe.ARRAY_SHORT_INDEX_SCALE)).add(int[].class, (Offsetter<int[]>)Offsetter.of(v -> PUnsafe.ARRAY_INT_BASE_OFFSET, v -> v.length * (long)PUnsafe.ARRAY_INT_INDEX_SCALE)).add(long[].class, (Offsetter<long[]>)Offsetter.of(v -> PUnsafe.ARRAY_LONG_BASE_OFFSET, v -> v.length * (long)PUnsafe.ARRAY_LONG_INDEX_SCALE)).add(float[].class, (Offsetter<float[]>)Offsetter.of(v -> PUnsafe.ARRAY_FLOAT_BASE_OFFSET, v -> v.length * (long)PUnsafe.ARRAY_FLOAT_INDEX_SCALE)).add(double[].class, (Offsetter<double[]>)Offsetter.of(v -> PUnsafe.ARRAY_DOUBLE_BASE_OFFSET, v -> v.length * (long)PUnsafe.ARRAY_DOUBLE_INDEX_SCALE)).add(char[].class, (Offsetter<char[]>)Offsetter.of(v -> PUnsafe.ARRAY_CHAR_BASE_OFFSET, v -> v.length * (long)PUnsafe.ARRAY_CHAR_INDEX_SCALE)).add(Object[].class, (Offsetter<Object[]>)Offsetter.of(v -> PUnsafe.ARRAY_OBJECT_BASE_OFFSET, v -> v.length * (long)PUnsafe.ARRAY_OBJECT_INDEX_SCALE)).add(this.classForName("java.nio.HeapByteBuffer"), (Offsetter<Object>)Offsetter.of(v -> PUnsafe.ARRAY_BYTE_BASE_OFFSET, Buffer::capacity, v -> PUnsafe.getObject(v, byteBuffer_hb_offset))).add(this.classForName("java.nio.HeapByteBufferR"), this.offsetters.get(this.classForName("java.nio.HeapByteBuffer"))).add(this.classForName("java.nio.DirectByteBuffer"), (Offsetter<Object>)Offsetter.of(v -> PUnsafe.getLong(v, buffer_address_offset), Buffer::capacity)).add(this.classForName("java.nio.DirectByteBufferR"), this.offsetters.get(this.classForName("java.nio.DirectByteBuffer")));
    }
    
    protected <T> Class<T> classForName(@NonNull final String canocialName) {
        if (canocialName == null) {
            throw new NullPointerException("canocialName");
        }
        try {
            return (Class<T>)Class.forName(canocialName);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    public <T> OffsetLookup add(@NonNull final Class<T> clazz, @NonNull final Offsetter<T> offsetter) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        if (offsetter == null) {
            throw new NullPointerException("offsetter");
        }
        this.offsetters.put(clazz, offsetter);
        return this;
    }
    
    public OffsetData getData(@NonNull final Object o) {
        if (o == null) {
            throw new NullPointerException("o");
        }
        final Class<?> clazz = o.getClass();
        if (Offsettable.class.isAssignableFrom(clazz)) {
            return ((Offsettable)o).data();
        }
        final Offsetter offsetter;
        if ((offsetter = this.offsetters.get(clazz)) != null) {
            return offsetter.data(o);
        }
        throw new IllegalArgumentException(String.format("Unregistered type: %s", clazz.getCanonicalName()));
    }
    
    public long getOffset(@NonNull final Object o) {
        if (o == null) {
            throw new NullPointerException("o");
        }
        final Class<?> clazz = o.getClass();
        if (Offsettable.class.isAssignableFrom(clazz)) {
            return ((Offsettable)o).memoryAddress();
        }
        final Offsetter offsetter;
        if ((offsetter = this.offsetters.get(clazz)) != null) {
            return offsetter.memoryOffset(o);
        }
        throw new IllegalArgumentException(String.format("Unregistered type: %s", clazz.getCanonicalName()));
    }
    
    public long getLength(@NonNull final Object o) {
        if (o == null) {
            throw new NullPointerException("o");
        }
        final Class<?> clazz = o.getClass();
        if (Offsettable.class.isAssignableFrom(clazz)) {
            return ((Offsettable)o).memorySize();
        }
        final Offsetter offsetter;
        if ((offsetter = this.offsetters.get(clazz)) != null) {
            return offsetter.memoryLength(o);
        }
        throw new IllegalArgumentException(String.format("Unregistered type: %s", clazz.getCanonicalName()));
    }
    
    public OffsetLookup() {
        this.offsetters = new IdentityHashMap<Class<?>, Offsetter>();
    }
    
    @NonNull
    public Map<Class<?>, Offsetter> getOffsetters() {
        return this.offsetters;
    }
    
    static {
        INSTANCE = new OffsetLookup().addDefault();
    }
}
