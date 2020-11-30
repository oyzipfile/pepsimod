// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common;

import java.lang.reflect.Array;
import lombok.NonNull;
import java.util.Iterator;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Deque;

public abstract class PCollections
{
    public static final Deque EMPTY_DEQUE;
    
    public static <T> Deque<T> emptyDeque() {
        return (Deque<T>)PCollections.EMPTY_DEQUE;
    }
    
    private PCollections() {
        throw new IllegalStateException();
    }
    
    static {
        EMPTY_DEQUE = new EmptyDeque();
    }
    
    protected static final class EmptyDeque<V> implements Deque<V>
    {
        @Override
        public void addFirst(final V v) {
            throw new IllegalStateException();
        }
        
        @Override
        public void addLast(final V v) {
            throw new IllegalStateException();
        }
        
        @Override
        public boolean offerFirst(final V v) {
            return false;
        }
        
        @Override
        public boolean offerLast(final V v) {
            return false;
        }
        
        @Override
        public V removeFirst() {
            throw new NoSuchElementException();
        }
        
        @Override
        public V removeLast() {
            throw new NoSuchElementException();
        }
        
        @Override
        public V pollFirst() {
            return null;
        }
        
        @Override
        public V pollLast() {
            return null;
        }
        
        @Override
        public V getFirst() {
            throw new NoSuchElementException();
        }
        
        @Override
        public V getLast() {
            throw new NoSuchElementException();
        }
        
        @Override
        public V peekFirst() {
            return null;
        }
        
        @Override
        public V peekLast() {
            return null;
        }
        
        @Override
        public boolean removeFirstOccurrence(final Object o) {
            return false;
        }
        
        @Override
        public boolean removeLastOccurrence(final Object o) {
            return false;
        }
        
        @Override
        public boolean add(final V v) {
            throw new IllegalStateException();
        }
        
        @Override
        public boolean offer(final V v) {
            return false;
        }
        
        @Override
        public V remove() {
            throw new NoSuchElementException();
        }
        
        @Override
        public V poll() {
            return null;
        }
        
        @Override
        public V element() {
            throw new NoSuchElementException();
        }
        
        @Override
        public V peek() {
            return null;
        }
        
        @Override
        public void push(final V v) {
            throw new IllegalStateException();
        }
        
        @Override
        public V pop() {
            return null;
        }
        
        @Override
        public boolean remove(final Object o) {
            return false;
        }
        
        @Override
        public boolean containsAll(final Collection<?> c) {
            return false;
        }
        
        @Override
        public boolean addAll(final Collection<? extends V> c) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean removeAll(final Collection<?> c) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean retainAll(final Collection<?> c) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void clear() {
        }
        
        @Override
        public boolean contains(final Object o) {
            return false;
        }
        
        @Override
        public int size() {
            return 0;
        }
        
        @Override
        public boolean isEmpty() {
            return true;
        }
        
        @Override
        public Iterator<V> iterator() {
            return new Iterator<V>() {
                @Override
                public boolean hasNext() {
                    return false;
                }
                
                @Override
                public V next() {
                    return null;
                }
            };
        }
        
        @Override
        public Object[] toArray() {
            return new Object[0];
        }
        
        @Override
        public <T> T[] toArray(@NonNull final T[] a) {
            if (a == null) {
                throw new NullPointerException("a");
            }
            if (a.length == 0) {
                return a;
            }
            return (T[])Array.newInstance(a.getClass().getComponentType(), 0);
        }
        
        @Override
        public Iterator<V> descendingIterator() {
            return this.iterator();
        }
    }
}
