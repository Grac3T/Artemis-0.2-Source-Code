package cc.ghast.artemis.v2.utils.maths;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class EvictingLinkedList<T> extends LinkedList<T> {
    private int maxSize;

    public EvictingLinkedList(int maxSize) {
        this.maxSize = maxSize;
    }

    public EvictingLinkedList(Collection<? extends T> c, int maxSize) {
        super(c);
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public boolean add(T t) {
        if (size() >= maxSize) removeFirst();
        return super.add(t);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return c.stream().anyMatch(this::add);
    }

    @Override
    public Stream<T> stream() {
        return new CopyOnWriteArrayList<>(this).stream();
    }
}