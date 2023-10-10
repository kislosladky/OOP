package ru.nsu.kislitsyn.tree;

import java.util.ArrayList;
import java.util.Iterator;

public class IteratorBfs<T> implements Iterable<Tree<T>> {
    private ArrayList<Tree<T>> arr = new ArrayList<>();

    public IteratorBfs (Tree<T> tree) {
        arr = this.bfs(tree);
    }

    private ArrayList<Tree<T>> bfs(Tree<T> node) {
        ArrayList<Tree<T>> queue = new ArrayList<>();
        while (node.getParent() != null) {
            node = node.getParent();
        }

        queue.add(node);
        while (!queue.isEmpty()) {
            arr.add(queue.get(0));
            queue.addAll(queue.get(0).getChildren());
            queue.remove(0);
        }

        return arr;
    }

    @Override
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
