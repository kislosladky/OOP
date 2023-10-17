package ru.nsu.kislitsyn.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
* This is my implementation of tree class with generic type.
*
* @param <T> is generic.
*/
public class Tree<T> implements Iterable<T> {
    private Tree<T> parent;
    private List<Tree<T>> children;
    private T value;
    private boolean changed;
    private boolean useBfs;

    /**
    * Constructor of tree node.
    *
    * @param value the value of node.
    */
    public Tree(T value) {
        this.value = value;
        this.children = new ArrayList<>();
        changed = false;
        useBfs = true;
    }

    /**
    * Getter for changed flag.
    *
    * @return the value of flag.
    */
    public boolean getChanged() {
        return this.changed;
    }

    /**
    * Setter for changed flag.
    *
    * @param value the value of the changed flag.
    */
    public void setChanged(boolean value) {
        this.changed = value;
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
        this.changed = true;
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
        return this.parent;
    }

    /**
    * Setter for useBfs.
    *
    * @param value the value of flag.
    */
    public void setUseBfs(boolean value) {
        this.useBfs = value;
    }

    /**
    * Getter for useBfs.
    *
    * @return the flag useBfs.
    */
    public boolean getUseBfs() {
        return this.useBfs;
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
        this.setChanged(true);
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
        this.setChanged(true);
        childTree.setParent(this);
        return childTree;
    }

    /**
    * removes the node in the tree.
    */
    public void remove() {
        this.setChanged(true);

        for (Tree<T> i : this.children) {
            i.setParent(this.getParent());
            this.getParent().addChild(i);
        }

        if (this.parent != null) {
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
        if (useBfs) {
            return new IteratorBfs<>(this);
        } else {
            return new IteratorDfs<>(this);
        }
    }

    /**
    * Compares trees.
    *
    * @param other is the tree we need to compare with.
    *
    * @return true, if the trees completely equal, otherwise false.
    */
    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }

        if (other == this) {
            return true;
        }

        var another = (Tree<T>) other;

        Iterator<T> iteratorThis = this.iterator();
        Iterator<T> iteratorB = another.iterator();

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

    @Override
    public int hashCode() {
        return Objects.hash(children, value, changed, useBfs);
    }

    /**
    * Checks if the node have changed.
    *
    * @return true if node have changed.
    */
    boolean changed() {
        if (this.getChanged()) {
            return true;
        }
        for (Tree<T> i : this.getChildren()) {
            if (i.changed()) {
                return true;
            }
        }
        return false;
    }

    /**
    * Sets tree to unchanged condition.
    */
    void unchanged() {
        this.changed = false;
        for (Tree<T> i : this.getChildren()) {
            i.unchanged();
        }
    }

//    enum iteratorType {
//        BFS,
//        DFS
//    }
}
