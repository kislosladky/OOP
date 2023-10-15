package ru.nsu.kislitsyn.tree;

import java.util.*;

//public class Tree<T> {
public class Tree<T> implements Iterable<T>{
    private Tree<T> parent;
    private List<Tree<T>> children;
    private T value;

    public Tree(T value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public Tree() {};

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<Tree<T>> getChildren() {
        return this.children;
    }

    public void setParent(Tree<T> parent) {
        this.parent = parent;
    }

    public Tree<T> getParent() {
        return parent;
    }

    public Tree<T> addChild(T childValue) {
        Tree<T> childTree = new Tree<>(childValue);
        this.children.add(childTree);
        childTree.setParent(this);
        return childTree;
    }

    public Tree<T> addChild(Tree<T> childTree) {
        children.add(childTree);
        childTree.setParent(this);
        return childTree;
    }

    public void remove() {
        if (this.parent != null) {
            for (Tree<T> i: this.children){
                i.parent = this.parent;
            }

            this.parent.children.remove(this);
            this.parent = null;
        }

        this.children.clear();
    }

    public Iterator<T> iterator() {
        IteratorBfs<T> myIterator = new IteratorBfs<>(this);
        Iterator<T> it = new Iterator<T>() {
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < myIterator.size();
            }

            @Override
            public T next() {
                return myIterator.get(currentIndex++);
            }
        };
        return it;
    }


    public boolean isEqual(Tree<T> another) {
        Iterator<T> iterThis = this.iterator();
        Iterator<T> iterB;
        if (another != null){
            iterB = another.iterator();
        } else {
            return false;
        }

        while (iterThis.hasNext() && iterB.hasNext()) {
            if (iterThis.next() != iterB.next()) {
                return false;
            }
        }
        if (iterThis.hasNext() || iterB.hasNext()) {
            return false;
        }
        return true;
    }

}
