package ru.nsu.kislitsyn.tree;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
* This is my implementation of tree class with generic type.
*
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


    /**
    * Getter for value.
    *
    * @return the value of the node.
    */
    public T getValue() {
        return this.value;
    }

    /**
    * Setter for value.
    *
    * @param value the value we need to set.
    */
    public void setValue(T value) {
        this.value = value;
    }

    /**
    * Getter for list of children.
    *
    * @return the list of the children.
    */
    public List<Tree<T>> getChildren() {
        return this.children;
    }

    /**
    * Setter for parent.
    *
    * @param parent is the parent we need to set.
    */
    public void setParent(Tree<T> parent) {
        this.parent = parent;
    }

    /**
    * Getter for parent.
    *
    * @return parent of the node.
    */
    public Tree<T> getParent() {
        return parent;
    }

    /**
    * Adds child to the node.
    *
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
    * Adds child to the node.
    *
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
    * Iterates tree.
    *
    * @return iterator of the tree.
    */
    public Iterator<T> iterator() {
        IteratorBfs<T> myIterator = new IteratorBfs<>(this);
        return new Iterator<T>() {
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
    }

    /**
    * Compares trees.
    *
    * @param another is the tree we need to compare with.
    *
    * @return true, if the trees completely equal, otherwise false.
    */
    public boolean isEqual(Tree<T> another) {
        Iterator<T> iteratorThis = this.iterator();
        Iterator<T> iteratorB;
        if (another != null) {
            iteratorB = another.iterator();
        } else {
            return false;
        }

        while (iteratorThis.hasNext() && iteratorB.hasNext()) {
            if (iteratorThis.next() != iteratorB.next()) {
                return false;
            }
        }
        if (iteratorThis.hasNext() || iteratorB.hasNext()) {
            return false;
        }
        return true;
    }
}
