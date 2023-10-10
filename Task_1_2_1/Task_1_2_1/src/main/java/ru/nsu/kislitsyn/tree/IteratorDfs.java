package ru.nsu.kislitsyn.tree;

import java.util.ArrayList;
import java.util.Iterator;

public class IteratorDfs<T> implements Iterable<Tree<T>> {
    private ArrayList<Tree<T>> arr = new ArrayList<>();

    public IteratorDfs(Tree<T> tree) {
        arr = this.almostdfs(tree);
    }

    private ArrayList<Tree<T>> almostdfs(Tree<T> node) {
        while (node.getParent() != null) {
            node = node.getParent();
        }
        dfs(node);
        return arr;
    }

    private void dfs(Tree<T> node) {
        arr.add(node);
        for (Tree<T> child : node.getChildren()) {
            dfs(child);
        }
    }

    public Iterator<Tree<T>> iterator() {
        Iterator<Tree<T>> answ = new Iterator<>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < arr.size();
            }

            @Override
            public Tree<T> next() {
                return arr.get(currentIndex++);
            }
        };
        return answ;
    }
}