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
    private final List<Tree<T>> children;
    private T value;
    private int changeCnt;
    private IteratorType iterType;

    /**
    * Constructor of tree node.
    *
    * @param value the value of node.
    */
    public Tree(T value) {
        this.value = value;
        this.children = new ArrayList<>();
        changeCnt = 0;
        iterType = IteratorType.BFS;
    }

    /**
    * Getter for changed flag.
    *
    * @return the value of flag.
    */
    public int getChanged() {
        return this.changeCnt;
    }

    /**
    * Setter for changed flag.
    */
    public void setChangeCnt() {
        this.changeCnt++;
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
        this.setChangeCnt();
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
    public void setIterType(IteratorType value) {
        this.iterType = value;
    }

    /**
    * Getter for useBfs.
    *
    * @return the flag useBfs.
    */
    public IteratorType getIterType() {
        return this.iterType;
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
        this.setChangeCnt();
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
        this.setChangeCnt();
        childTree.setParent(this);
        return childTree;
    }

    /**
    * removes the node in the tree.
    */
    public void remove() {
        this.setChangeCnt();

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
        if (iterType == IteratorType.BFS) {
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
    @SuppressWarnings("unchecked")
    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }

        if (other == this) {
            return true;
        }

        var another = (Tree<T>) other;

        if (this.value != another.value) {
            return false;
        } else {
            return this.hashCode() == other.hashCode();
        }

    }

    @Override
    public int hashCode() {
        int answ = Objects.hash(value);
        for (Tree<T> i : this.children) {
            answ += i.hashCode();
        }
        return answ;
    }

    /**
     * enum for choosing way to iterate.
     */
    public enum IteratorType {
        BFS,
        DFS
    }
}
