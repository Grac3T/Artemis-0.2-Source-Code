/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.utils.maths;

import java.util.Deque;
import java.util.Iterator;

public interface EvictingDeque<E>
extends Deque<E> {
    @Override
    public void addFirst(E var1);

    @Override
    public void addLast(E var1);

    @Override
    public boolean offerFirst(E var1);

    @Override
    public boolean offerLast(E var1);

    @Override
    public E removeFirst();

    @Override
    public E removeLast();

    @Override
    public E pollFirst();

    @Override
    public E pollLast();

    @Override
    public E getFirst();

    @Override
    public E getLast();

    @Override
    public E peekFirst();

    @Override
    public E peekLast();

    @Override
    public boolean removeFirstOccurrence(Object var1);

    @Override
    public boolean removeLastOccurrence(Object var1);

    @Override
    public boolean add(E var1);

    @Override
    public boolean offer(E var1);

    @Override
    public E remove();

    @Override
    public E poll();

    @Override
    public E element();

    @Override
    public E peek();

    @Override
    public void push(E var1);

    @Override
    public E pop();

    @Override
    public boolean remove(Object var1);

    @Override
    public boolean contains(Object var1);

    @Override
    public int size();

    @Override
    public Iterator<E> iterator();

    @Override
    public Iterator<E> descendingIterator();
}

