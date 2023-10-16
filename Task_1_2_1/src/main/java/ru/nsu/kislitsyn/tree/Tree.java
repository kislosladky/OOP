package ru.nsu.kislitsyn.tree;

import java.util.*;

/**
* @param <T> is generic.
*/
public class Tree<T> implements Iterable<T> {
    private Tree<T> parent;
    private List<Tree<T>> children;
    private T value;

    public Tree(T value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public Tree() {
    }

    ;

    /**
    * @return the value of the node.
    */
    public T getValue() {
        return this.value;
    }

    /**
    * @param value the value we need to set.
    */
    public void setValue(T value) {
        this.value = value;
    }

    /**
    * @return the list of the children.
    */
    public List<Tree<T>> getChildren() {
        return this.children;
    }

    /**
    * @param parent is the parent we need to set.
    */
    public void setParent(Tree<T> parent) {
        this.parent = parent;
    }

    /**
    * @return parent of the node.
    */
    public Tree<T> getParent() {
        return parent;
    }

    /**
    * @param childValue the value of the node we need to add to the tree.
    *
    * @return the node itself.
    */
    public Tree<T> addChild(T childValue) {
        Tree<T> childTree = new Tree<>(childValue);
        this.children.add(childTree);
        childTree.setParent(this);
        return childTree;
    }

    /**
    * @param childTree is the node we need to add to the tree.
    *
    * @return node itself.
    */
    public Tree<T> addChild(Tree<T> childTree) {
        children.add(childTree);
        childTree.setParent(this);
        return childTree;
    }

    /**
    * removes the node in the tree.
    */
    public void remove() {
        if (this.parent != null) {
            for (Tree<T> i : this.children) {
                i.parent = this.parent;
            }

            this.parent.children.remove(this);
            this.parent = null;
        }

        this.children.clear();
    }

    /**
    * @return iterator of the tree.
    */
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

    /**
    * @param another is the tree we need to compare with.
    *
    * @return true, if the trees completely equal, otherwise false.
    */
    public boolean isEqual(Tree<T> another) {
        Iterator<T> iterThis = this.iterator();
        Iterator<T> iterB;
        if (another != null) {
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
