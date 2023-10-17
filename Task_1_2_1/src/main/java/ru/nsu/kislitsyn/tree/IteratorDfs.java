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
    private Stack<Tree<T>> queue;
    private Tree<T> root;

    public IteratorDfs(Tree<T> root) {
        queue = new Stack<Tree<T>>();
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
        Tree<T> answ = queue.pop();
        queue.addAll(answ.getChildren());
        return answ.getValue();
    }

}
