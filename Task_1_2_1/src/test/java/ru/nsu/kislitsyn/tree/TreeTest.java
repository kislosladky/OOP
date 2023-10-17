package ru.nsu.kislitsyn.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;


class TreeTest {

    @Test
    void getValueTest() {
        Tree<String> tree = new Tree<>("R1");
        assertEquals("R1", tree.getValue());
    }

    @Test
    void taskTest() {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        assertEquals("A", a.getValue());
        assertEquals("A", tree.getChildren().get(0).getValue());

        var b = a.addChild("B");
        assertEquals("B", b.getValue());
        assertEquals("B", a.getChildren().get(0).getValue());
        assertEquals("B", tree.getChildren().get(0).getChildren().get(0).getValue());
        assertEquals("R1", a.getParent().getValue());
        assertEquals("R1", b.getParent().getParent().getValue());
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        b.remove();
        assertEquals(0, a.getChildren().size());
    }

    @Test
    void bfsTest() {
        Tree<String> tree = new Tree<>("R1");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        var a = tree.addChild("A");
        var b = a.addChild("B");
        b.remove();
        System.out.println("BFS");
        for (String i : tree) {
            System.out.println(i);
        }
    }

    @Test
    void addChildValueTest() {
        var a = new Tree<>(2);
        assertEquals(2, a.getValue());
        a.addChild(6);
        assertEquals(6, a.getChildren().get(0).getValue());
    }

    @Test
    void addChildTreeTest() {
        var a = new Tree<>(2);
        assertEquals(2, a.getValue());
        a.addChild(new Tree<>(6));
        assertEquals(6, a.getChildren().get(0).getValue());
    }

    @Test
    void removeTest() {
        var a = new Tree<>(2);
        assertEquals(2, a.getValue());
        a.addChild(3);
        var b = a.addChild(new Tree<>(6));
        b.remove();
        assertEquals(1, a.getChildren().size());
        assertEquals(3, a.getChildren().get(0).getValue());
    }

    @Test
    void equalTest() {
        var a = new Tree<>("A");
        var childA = a.addChild("a");
        childA.addChild("aa");

        var copyA = new Tree<>("A");
        var copyAChild = copyA.addChild("a");
        copyAChild.addChild("aa");

        assertEquals(a, copyA);
    }

    @Test
    void equalTestFalse() {
        var a = new Tree<>("A");
        var childA = a.addChild("a");
        childA.addChild("aa");

        var copyA = new Tree<>("A");
        var copyAChild = copyA.addChild("a");
        copyAChild.addChild("aaa");

        assertNotEquals(a, copyA);
    }

    /**
     * This test shouldn't work, but works, because my method isn't perfect.
     */
    @Test
    void equalTestOfNotEqualTrees() {
        var a = new Tree<>("A");
        var childA = a.addChild("a");
        a.addChild("aa");

        var b = new Tree<>("A");
        var childB = b.addChild("a");
        childB.addChild("aa");

        assertEquals(a, b);
    }



    @Test
    void dfsTest() {
        Tree<String> tree = new Tree<>("R1");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        var a = tree.addChild("A");
        var b = a.addChild("B");
        b.remove();
        tree.setUseBfs(false);
        System.out.println("DFS");
        for (String i : tree) {
            System.out.println(i);
        }
    }

}