package ru.nsu.kislitsyn.tree;

import java.util.ArrayList;
import java.util.Iterator;


/**
* @param <T> is generic.
*/
class IteratorBfs<T> implements Iterator<T> {
    private ArrayList<Tree<T>> arr = new ArrayList<>();
    private int currentIndex;

    public IteratorBfs(Tree<T> tree) {
        arr = this.bfs(tree);
        currentIndex = 0;
    }

    /**
    * @param node the node that is the part of the tree we want to iterate via bfs.
    *
    * @return the list of the nodes of the tree.
    */
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

    /**
    * @return the size of the list.
    */
    public int size() {
        return arr.size();
    }

    /**
    * @param index the index of the node which value we want to get.
    *
    * @return the value of the node.
    */
    public T get(int index) {
        return arr.get(index).getValue();
    }

    /**
    * @return true, if the previous node was not the last. Otherwise, false.
    */
    @Override
    public boolean hasNext() {
        return currentIndex < arr.size() - 1;
    }

    /**
    * @return the value of the next node.
    */
    @Override
    public T next() {
        return arr.get(currentIndex++).getValue();
    }
}
