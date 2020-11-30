// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.unsafe;

import java.security.ProtectionDomain;
import java.lang.reflect.Field;
import lombok.NonNull;
import sun.misc.Unsafe;

public abstract class PUnsafe
{
    public static final Unsafe UNSAFE;
    public static final int ARRAY_BOOLEAN_BASE_OFFSET;
    public static final int ARRAY_BYTE_BASE_OFFSET;
    public static final int ARRAY_SHORT_BASE_OFFSET;
    public static final int ARRAY_CHAR_BASE_OFFSET;
    public static final int ARRAY_INT_BASE_OFFSET;
    public static final int ARRAY_LONG_BASE_OFFSET;
    public static final int ARRAY_FLOAT_BASE_OFFSET;
    public static final int ARRAY_DOUBLE_BASE_OFFSET;
    public static final int ARRAY_OBJECT_BASE_OFFSET;
    public static final int ARRAY_BOOLEAN_INDEX_SCALE;
    public static final int ARRAY_BYTE_INDEX_SCALE;
    public static final int ARRAY_SHORT_INDEX_SCALE;
    public static final int ARRAY_CHAR_INDEX_SCALE;
    public static final int ARRAY_INT_INDEX_SCALE;
    public static final int ARRAY_LONG_INDEX_SCALE;
    public static final int ARRAY_FLOAT_INDEX_SCALE;
    public static final int ARRAY_DOUBLE_INDEX_SCALE;
    public static final int ARRAY_OBJECT_INDEX_SCALE;
    public static final ThreadLocal<Object[]> objArrCache;
    
    public static int getInt(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getInt(o, pos);
    }
    
    public static void putInt(final Object o, final long pos, final int val) {
        PUnsafe.UNSAFE.putInt(o, pos, val);
    }
    
    public static <T> T getObject(final Object o, final long pos) {
        return (T)PUnsafe.UNSAFE.getObject(o, pos);
    }
    
    public static void putObject(final Object o, final long pos, final Object val) {
        PUnsafe.UNSAFE.putObject(o, pos, val);
    }
    
    public static boolean getBoolean(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getBoolean(o, pos);
    }
    
    public static void putBoolean(final Object o, final long pos, final boolean val) {
        PUnsafe.UNSAFE.putBoolean(o, pos, val);
    }
    
    public static byte getByte(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getByte(o, pos);
    }
    
    public static void putByte(final Object o, final long pos, final byte val) {
        PUnsafe.UNSAFE.putByte(o, pos, val);
    }
    
    public static short getShort(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getShort(o, pos);
    }
    
    public static void putShort(final Object o, final long pos, final short val) {
        PUnsafe.UNSAFE.putShort(o, pos, val);
    }
    
    public static char getChar(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getChar(o, pos);
    }
    
    public static void putChar(final Object o, final long pos, final char val) {
        PUnsafe.UNSAFE.putChar(o, pos, val);
    }
    
    public static long getLong(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getLong(o, pos);
    }
    
    public static void putLong(final Object o, final long pos, final long val) {
        PUnsafe.UNSAFE.putLong(o, pos, val);
    }
    
    public static float getFloat(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getFloat(o, pos);
    }
    
    public static void putFloat(final Object o, final long pos, final float val) {
        PUnsafe.UNSAFE.putFloat(o, pos, val);
    }
    
    public static double getDouble(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getDouble(o, pos);
    }
    
    public static void putDouble(final Object o, final long pos, final double val) {
        PUnsafe.UNSAFE.putDouble(o, pos, val);
    }
    
    public static int getInt(final long pos) {
        return PUnsafe.UNSAFE.getInt(pos);
    }
    
    public static void putInt(final long pos, final int val) {
        PUnsafe.UNSAFE.putInt(pos, val);
    }
    
    public static byte getByte(final long pos) {
        return PUnsafe.UNSAFE.getByte(pos);
    }
    
    public static void putByte(final long pos, final byte val) {
        PUnsafe.UNSAFE.putByte(pos, val);
    }
    
    public static short getShort(final long pos) {
        return PUnsafe.UNSAFE.getShort(pos);
    }
    
    public static void putShort(final long pos, final short val) {
        PUnsafe.UNSAFE.putShort(pos, val);
    }
    
    public static char getChar(final long pos) {
        return PUnsafe.UNSAFE.getChar(pos);
    }
    
    public static void putChar(final long pos, final char val) {
        PUnsafe.UNSAFE.putChar(pos, val);
    }
    
    public static long getLong(final long pos) {
        return PUnsafe.UNSAFE.getLong(pos);
    }
    
    public static void putLong(final long pos, final long val) {
        PUnsafe.UNSAFE.putLong(pos, val);
    }
    
    public static float getFloat(final long pos) {
        return PUnsafe.UNSAFE.getFloat(pos);
    }
    
    public static void putFloat(final long pos, final float val) {
        PUnsafe.UNSAFE.putFloat(pos, val);
    }
    
    public static double getDouble(final long pos) {
        return PUnsafe.UNSAFE.getDouble(pos);
    }
    
    public static void putDouble(final long pos, final double val) {
        PUnsafe.UNSAFE.putDouble(pos, val);
    }
    
    public static long getAddress(final long pos) {
        return PUnsafe.UNSAFE.getAddress(pos);
    }
    
    public static void putAddress(final long pos, final long val) {
        PUnsafe.UNSAFE.putAddress(pos, val);
    }
    
    public static long allocateMemory(final long size) {
        return PUnsafe.UNSAFE.allocateMemory(size);
    }
    
    public static long allocateMemory(@NonNull final Object cleanerTarget, final long size) {
        if (cleanerTarget == null) {
            throw new NullPointerException("cleanerTarget");
        }
        final long offset = PUnsafe.UNSAFE.allocateMemory(size);
        PCleaner.cleaner(cleanerTarget, offset);
        return offset;
    }
    
    public static long reallocateMemory(final long oldAddress, final long size) {
        return PUnsafe.UNSAFE.reallocateMemory(oldAddress, size);
    }
    
    public static void setMemory(final Object o, final long pos, final long length, final byte val) {
        PUnsafe.UNSAFE.setMemory(o, pos, length, val);
    }
    
    public static void setMemory(final long pos, final long length, final byte val) {
        PUnsafe.UNSAFE.setMemory(null, pos, length, val);
    }
    
    public static void copyMemory(final Object src, final long srcOffset, final Object dst, final long dstOffset, final long length) {
        PUnsafe.UNSAFE.copyMemory(src, srcOffset, dst, dstOffset, length);
    }
    
    public static void copyMemory(final long src, final long dst, final long length) {
        PUnsafe.UNSAFE.copyMemory(null, src, null, dst, length);
    }
    
    public static void freeMemory(final long address) {
        PUnsafe.UNSAFE.freeMemory(address);
    }
    
    public static long staticFieldOffset(final Field field) {
        return PUnsafe.UNSAFE.staticFieldOffset(field);
    }
    
    public static long objectFieldOffset(final Field field) {
        return PUnsafe.UNSAFE.objectFieldOffset(field);
    }
    
    public static Object staticFieldBase(final Field field) {
        return PUnsafe.UNSAFE.staticFieldBase(field);
    }
    
    public static boolean shouldBeInitialized(final Class<?> clazz) {
        return PUnsafe.UNSAFE.shouldBeInitialized(clazz);
    }
    
    public static void ensureClassInitialized(final Class<?> clazz) {
        PUnsafe.UNSAFE.ensureClassInitialized(clazz);
    }
    
    public static int arrayBaseOffset(final Class<?> clazz) {
        return PUnsafe.UNSAFE.arrayBaseOffset(clazz);
    }
    
    public static int arrayIndexScale(final Class<?> clazz) {
        return PUnsafe.UNSAFE.arrayIndexScale(clazz);
    }
    
    public static int addressSize() {
        return PUnsafe.UNSAFE.addressSize();
    }
    
    public static int pageSize() {
        return PUnsafe.UNSAFE.pageSize();
    }
    
    public static Class<?> defineClass(final String name, final byte[] classBytes, final int off, final int len, final ClassLoader srcLoader, final ProtectionDomain domain) {
        return (Class<?>)PUnsafe.UNSAFE.defineClass(name, classBytes, off, len, srcLoader, domain);
    }
    
    public static Class<?> defineAnonymousClass(final Class<?> hostClass, final byte[] data, final Object[] cpPatches) {
        return PUnsafe.UNSAFE.defineAnonymousClass(hostClass, data, cpPatches);
    }
    
    public static <T> T allocateInstance(@NonNull final Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        try {
            return (T)PUnsafe.UNSAFE.allocateInstance(clazz);
        }
        catch (InstantiationException e) {
            PUnsafe.UNSAFE.throwException(e);
            throw new RuntimeException(e);
        }
    }
    
    public static void throwException(final Throwable t) {
        PUnsafe.UNSAFE.throwException(t);
    }
    
    public static boolean compareAndSwapObject(final Object o, final long pos, final Object expected, final Object newValue) {
        return PUnsafe.UNSAFE.compareAndSwapObject(o, pos, expected, newValue);
    }
    
    public static boolean compareAndSwapInt(final Object o, final long pos, final int expected, final int newValue) {
        return PUnsafe.UNSAFE.compareAndSwapInt(o, pos, expected, newValue);
    }
    
    public static boolean compareAndSwapLong(final Object o, final long pos, final long expected, final long newValue) {
        return PUnsafe.UNSAFE.compareAndSwapLong(o, pos, expected, newValue);
    }
    
    public static <T> T getObjectVolatile(final Object o, final long pos) {
        return (T)PUnsafe.UNSAFE.getObjectVolatile(o, pos);
    }
    
    public static void putObjectVolatile(final Object o, final long pos, final Object val) {
        PUnsafe.UNSAFE.putObjectVolatile(o, pos, val);
    }
    
    public static int getIntVolatile(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getIntVolatile(o, pos);
    }
    
    public static void putIntVolatile(final Object o, final long pos, final int val) {
        PUnsafe.UNSAFE.putIntVolatile(o, pos, val);
    }
    
    public static boolean getBooleanVolatile(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getBooleanVolatile(o, pos);
    }
    
    public static void putBooleanVolatile(final Object o, final long pos, final boolean val) {
        PUnsafe.UNSAFE.putBooleanVolatile(o, pos, val);
    }
    
    public static byte getByteVolatile(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getByteVolatile(o, pos);
    }
    
    public static void putByteVolatile(final Object o, final long pos, final byte val) {
        PUnsafe.UNSAFE.putByteVolatile(o, pos, val);
    }
    
    public static short getShortVolatile(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getShortVolatile(o, pos);
    }
    
    public static void putShortVolatile(final Object o, final long pos, final short val) {
        PUnsafe.UNSAFE.putShortVolatile(o, pos, val);
    }
    
    public static char getCharVolatile(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getCharVolatile(o, pos);
    }
    
    public static void putCharVolatile(final Object o, final long pos, final char val) {
        PUnsafe.UNSAFE.putCharVolatile(o, pos, val);
    }
    
    public static long getLongVolatile(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getLongVolatile(o, pos);
    }
    
    public static void putLongVolatile(final Object o, final long pos, final long val) {
        PUnsafe.UNSAFE.putLongVolatile(o, pos, val);
    }
    
    public static float getFloatVolatile(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getFloatVolatile(o, pos);
    }
    
    public static void putFloatVolatile(final Object o, final long pos, final float val) {
        PUnsafe.UNSAFE.putFloatVolatile(o, pos, val);
    }
    
    public static double getDoubleVolatile(final Object o, final long pos) {
        return PUnsafe.UNSAFE.getDoubleVolatile(o, pos);
    }
    
    public static void putDoubleVolatile(final Object o, final long pos, final double val) {
        PUnsafe.UNSAFE.putDoubleVolatile(o, pos, val);
    }
    
    public static void putOrderedObject(final Object o, final long pos, final Object val) {
        PUnsafe.UNSAFE.putOrderedObject(o, pos, val);
    }
    
    public static void putOrderedInt(final Object o, final long pos, final int val) {
        PUnsafe.UNSAFE.putOrderedInt(o, pos, val);
    }
    
    public static void putOrderedLong(final Object o, final long pos, final long val) {
        PUnsafe.UNSAFE.putOrderedLong(o, pos, val);
    }
    
    public static void unpark(final Object thread) {
        PUnsafe.UNSAFE.unpark(thread);
    }
    
    public static void park(final boolean absolute, final long time) {
        PUnsafe.UNSAFE.park(absolute, time);
    }
    
    public static int getLoadAverage(final double[] loadavg, final int nelems) {
        return PUnsafe.UNSAFE.getLoadAverage(loadavg, nelems);
    }
    
    public static int getAndAddInt(final Object o, final long pos, final int val) {
        return PUnsafe.UNSAFE.getAndAddInt(o, pos, val);
    }
    
    public static long getAndAddLong(final Object o, final long pos, final long val) {
        return PUnsafe.UNSAFE.getAndAddLong(o, pos, val);
    }
    
    public static int getAndSetInt(final Object o, final long pos, final int val) {
        return PUnsafe.UNSAFE.getAndSetInt(o, pos, val);
    }
    
    public static long getAndSetLong(final Object o, final long pos, final long val) {
        return PUnsafe.UNSAFE.getAndSetLong(o, pos, val);
    }
    
    public static <T> T getAndSetObject(final Object o, final long pos, final Object val) {
        return (T)PUnsafe.UNSAFE.getAndSetObject(o, pos, val);
    }
    
    public static void loadFence() {
        PUnsafe.UNSAFE.loadFence();
    }
    
    public static void storeFence() {
        PUnsafe.UNSAFE.storeFence();
    }
    
    public static void fullFence() {
        PUnsafe.UNSAFE.fullFence();
    }
    
    public static long pork_getOffset(@NonNull final Class clazz, @NonNull final String fieldName) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        if (fieldName == null) {
            throw new NullPointerException("fieldName");
        }
        try {
            return PUnsafe.UNSAFE.objectFieldOffset(clazz.getDeclaredField(fieldName));
        }
        catch (NoSuchFieldException e) {
            PUnsafe.UNSAFE.throwException(e);
            throw new RuntimeException(e);
        }
    }
    
    public static <V> V pork_swapObject(final Object o, final long pos, final Object newValue) {
        Object v;
        do {
            v = getObjectVolatile(o, pos);
        } while (!compareAndSwapObject(o, pos, v, newValue));
        return (V)v;
    }
    
    public static boolean pork_checkSwapIfNonNull(final Object o, final long pos, final Object newValue) {
        Object v;
        while ((v = getObjectVolatile(o, pos)) != null) {
            if (compareAndSwapObject(o, pos, v, newValue)) {
                return true;
            }
        }
        return false;
    }
    
    public static <V> V pork_swapIfNonNull(final Object o, final long pos, final Object newValue) {
        Object v;
        while ((v = getObjectVolatile(o, pos)) != null) {
            if (compareAndSwapObject(o, pos, v, newValue)) {
                return (V)v;
            }
        }
        return null;
    }
    
    private PUnsafe() {
        throw new IllegalStateException();
    }
    
    static {
        Unsafe unsafe = null;
        try {
            final Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
        }
        catch (NoSuchFieldException ex) {}
        catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to obtain instance of sun.misc.Unsafe", e);
        }
        finally {
            if ((UNSAFE = unsafe) == null) {
                throw new RuntimeException("Unable to obtain instance of sun.misc.Unsafe");
            }
        }
        ARRAY_BOOLEAN_BASE_OFFSET = arrayBaseOffset(boolean[].class);
        ARRAY_BYTE_BASE_OFFSET = arrayBaseOffset(byte[].class);
        ARRAY_SHORT_BASE_OFFSET = arrayBaseOffset(short[].class);
        ARRAY_CHAR_BASE_OFFSET = arrayBaseOffset(char[].class);
        ARRAY_INT_BASE_OFFSET = arrayBaseOffset(int[].class);
        ARRAY_LONG_BASE_OFFSET = arrayBaseOffset(long[].class);
        ARRAY_FLOAT_BASE_OFFSET = arrayBaseOffset(float[].class);
        ARRAY_DOUBLE_BASE_OFFSET = arrayBaseOffset(double[].class);
        ARRAY_OBJECT_BASE_OFFSET = arrayBaseOffset(Object[].class);
        ARRAY_BOOLEAN_INDEX_SCALE = arrayIndexScale(boolean[].class);
        ARRAY_BYTE_INDEX_SCALE = arrayIndexScale(byte[].class);
        ARRAY_SHORT_INDEX_SCALE = arrayIndexScale(short[].class);
        ARRAY_CHAR_INDEX_SCALE = arrayIndexScale(char[].class);
        ARRAY_INT_INDEX_SCALE = arrayIndexScale(int[].class);
        ARRAY_LONG_INDEX_SCALE = arrayIndexScale(long[].class);
        ARRAY_FLOAT_INDEX_SCALE = arrayIndexScale(float[].class);
        ARRAY_DOUBLE_INDEX_SCALE = arrayIndexScale(double[].class);
        ARRAY_OBJECT_INDEX_SCALE = arrayIndexScale(Object[].class);
        objArrCache = ThreadLocal.withInitial(() -> new Object[1]);
    }
}
