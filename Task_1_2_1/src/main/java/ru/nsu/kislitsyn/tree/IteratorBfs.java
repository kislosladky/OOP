package ru.nsu.kislitsyn.tree;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;


/**
 * This is an iterator for tree implemented via breadth first search.
 *
 * @param <T> generic type.
 */
public class IteratorBfs<T> implements Iterator<T> {
    private final List<Tree<T>> queue;
    private final Tree<T> root;
    private final int expectedChanges;

    /**
     * The constructor of IteratorBfs.
     *
     * @param root the node we should start bfs from.
     */
    public IteratorBfs(Tree<T> root) {
        queue = new ArrayList<>();
        queue.add(root);
        this.root = root;
        expectedChanges = root.getChanged();
    }

    @Override
    public boolean hasNext() {
        if (root.getChanged() != expectedChanges) {
            throw new ConcurrentModificationException();
        }
        return !queue.isEmpty();
    }

    @Override
    public T next() {
        if (root.getChanged() != expectedChanges) {
            throw new ConcurrentModificationException();
        }
        T answ = queue.get(0).getValue();
        queue.addAll(queue.get(0).getChildren());
        queue.remove(0);
        return answ;
    }
}

