package ru.nsu.kislitsyn.tree;

import java.util.*;

public class Tree<T> {
    private Tree<T> parent;
    private ArrayList<Tree<T>> children;
    private T value;

    public Tree(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public ArrayList<Tree<T>> getChildren() {
        return this.children;
    }

    public void setParent(Tree<T> parent) {
        this.parent = parent;
    }

    public Tree<T> getParent() {
        return parent;
    }

    public Tree<T> addChild(T child) {
        Tree<T> childTree = new Tree<>(child);
        children.add(childTree);
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
            this.parent.children.remove(this);
        }
        this.children.clear();
    }

}