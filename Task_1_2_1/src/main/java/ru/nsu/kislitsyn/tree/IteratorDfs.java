package ru.nsu.kislitsyn.tree;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;

/**
 * This is an iterator for tree implemented via depth first search.
 *
 * @param <T> generic type.
 */
public class IteratorDfs<T> implements Iterator<T> {
    private final Stack<Tree<T>> queue;
    private final Tree<T> root;
    private final int expectedChanges;

    /**
    * The constructor of IteratorDfs.
    *
    * @param root the node we should iterate from.
    */
    public IteratorDfs(Tree<T> root) {
        queue = new Stack<>();
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
        Tree<T> answ = queue.pop();
        queue.addAll(answ.getChildren());
        return answ.getValue();
    }

}
