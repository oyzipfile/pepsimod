// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.reference;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.daporkchop.lib.common.util.PorkUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import net.daporkchop.lib.common.util.PConstants;
import lombok.NonNull;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.Map;

public class InstancePool
{
    protected static final Map<Class<?>, Object> MAP;
    protected static final ReadWriteLock LOCK;
    
    public static <T> T getInstance(@NonNull final Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        InstancePool.LOCK.readLock().lock();
        T val;
        try {
            val = (T)InstancePool.MAP.get(clazz);
        }
        finally {
            InstancePool.LOCK.readLock().unlock();
        }
        if (val == null) {
            try {
                final Constructor<T> constructor = clazz.getDeclaredConstructor((Class<?>[])new Class[0]);
                constructor.setAccessible(true);
                val = constructor.newInstance(new Object[0]);
                InstancePool.LOCK.writeLock().lock();
                try {
                    InstancePool.MAP.putIfAbsent(clazz, val);
                }
                finally {
                    InstancePool.LOCK.writeLock().unlock();
                }
            }
            catch (NoSuchMethodException e) {
                throw new IllegalStateException(String.format("Class %s doesn't have a no-args constructor!", clazz.getCanonicalName()), e);
            }
            catch (IllegalAccessException e2) {
                throw PConstants.p_exception(e2);
            }
            catch (InstantiationException | InvocationTargetException ex2) {
                final ReflectiveOperationException ex;
                final ReflectiveOperationException e3 = ex;
                throw new IllegalStateException(String.format("Exception while invoking no-args constructor on class %s!", clazz.getCanonicalName()), e3);
            }
        }
        return val;
    }
    
    protected InstancePool() {
    }
    
    static {
        MAP = PorkUtil.newSoftCache();
        LOCK = new ReentrantReadWriteLock();
    }
}
