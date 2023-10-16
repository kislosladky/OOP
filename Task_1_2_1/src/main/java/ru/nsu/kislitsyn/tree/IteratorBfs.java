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
    private List<Tree<T>> queue;
    private Tree<T> root;
    public IteratorBfs(Tree<T> root) {
        queue = new ArrayList<Tree<T>>();
        queue.add(root);
        this.root = root;
        root.unchanged();
    }
    @Override
    public boolean hasNext() {
        if (root.changed()) {
            throw new ConcurrentModificationException();
        }
        if (queue.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public T next() {
        if (root.changed()) {
            throw new ConcurrentModificationException();
        }
        T answ = queue.get(0).getValue();
        queue.addAll(queue.get(0).getChildren());
        queue.remove(0);
        return answ;
    }
}

