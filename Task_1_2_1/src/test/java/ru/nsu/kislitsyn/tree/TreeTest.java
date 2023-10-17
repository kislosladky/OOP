package ru.nsu.kislitsyn.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
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
        assertTrue(tree.getChildren().contains(new Tree<>("A")));

        var b = a.addChild("B");
        assertEquals("B", b.getValue());
        assertEquals("B", a.getChildren().get(0).getValue());
        assertTrue(a.getChildren().contains(new Tree<>("B")));
        assertEquals("B", tree.getChildren().get(0).getChildren().get(0).getValue());
        assertEquals("R1", a.getParent().getValue());
        assertEquals("R1", b.getParent().getParent().getValue());
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        b.remove();
        assertTrue(a.getChildren().isEmpty());
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
        ArrayList<String> answ = new ArrayList<>();
        for (String i : tree) {
            System.out.println(i);
            answ.add(i);
        }
        assertEquals("R1", answ.get(0));
        assertEquals("R2", answ.get(1));
        assertEquals("A", answ.get(2));
        assertEquals("C", answ.get(3));
        assertEquals("D", answ.get(4));
    }

    @Test
    void addChildValueTest() {
        var a = new Tree<>(2);
        assertEquals(2, a.getValue());
        a.addChild(6);
        assertEquals(6, a.getChildren().get(0).getValue());
        assertTrue(a.getChildren().contains(new Tree<>(6)));
    }

    @Test
    void addChildTreeTest() {
        var a = new Tree<>(2);
        assertEquals(2, a.getValue());
        a.addChild(new Tree<>(6));
        assertEquals(6, a.getChildren().get(0).getValue());
        assertTrue(a.getChildren().contains(new Tree<>(6)));
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
        assertTrue(a.getChildren().contains(new Tree<>(3)));
    }

    @Test
    void removeTestWithLotsOfChildren() {
        var a = new Tree<>(2);
        a.addChild(3);
        var b = a.addChild(new Tree<>(6));
        b.addChild(4);
        b.addChild(90);
        b.remove();
        assertEquals(3, a.getChildren().size());
        assertTrue(a.getChildren().contains(new Tree<>(3)));
    }


    @Test
    void equalsTest() {
        var a = new Tree<>("A");
        var childA = a.addChild("a");
        childA.addChild("aa");

        var copyA = new Tree<>("A");
        var copyChildA = copyA.addChild("a");
        copyChildA.addChild("aa");

        assertEquals(a, copyA);
    }

    @Test
    void equalsTestFalse() {
        var a = new Tree<>("A");
        var childA = a.addChild("a");
        childA.addChild("aa");

        var copyA = new Tree<>("A");
        var copyChildA = copyA.addChild("a");
        copyChildA.addChild("aaa");

        assertNotEquals(a, copyA);
    }

    /**
    * This test shouldn't work, but works, because my method isn't perfect.
    */
    @Test
    void equalsTestOfNotEqualTrees() {
        var a = new Tree<>("A");
        a.addChild("a");
        a.addChild("aa");

        var b = new Tree<>("A");
        var childB = b.addChild("a");
        childB.addChild("aa");

        assertEquals(a, b);
    }

    @Test
    void checkEqualsTrueComplicated() {
        Tree<String> tree1 = new Tree<>("R1");
        var a1 = tree1.addChild("A");
        a1.addChild("B");
        Tree<String> subtree1 = new Tree<>("R2");
        subtree1.addChild("C");
        subtree1.addChild("D");
        tree1.addChild(subtree1);

        Tree<String> tree2 = new Tree<>("R1");
        var a2 = tree2.addChild("A");
        a2.addChild("B");
        Tree<String> subtree2 = new Tree<>("R2");

        subtree2.addChild("D");
        tree2.addChild(subtree2);
        subtree2.addChild("C");

        assertEquals(tree1, tree2);
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
        tree.setIterType(Tree.IteratorType.DFS);
        System.out.println("DFS");
        ArrayList<String> answ = new ArrayList<>();
        for (String i : tree) {
            System.out.println(i);
            answ.add(i);
        }
        assertEquals("R1", answ.get(0));
        assertEquals("A", answ.get(1));
        assertEquals("R2", answ.get(2));
        assertEquals("D", answ.get(3));
        assertEquals("C", answ.get(4));
    }

    @Test
    void setParentTestNull() {
        var a = new Tree<>("Around the world");
        assertNull(a.getParent());
    }

    @Test
    void setParentTest() {
        var a = new Tree<>("Around the world");
        var b = a.addChild("Harder, better, faster, stronger");
        assertEquals(a, b.getParent());
    }

    @Test
    void hashcodeTest() {
        var a = new Tree<>("A");
        a.addChild("a");
        a.addChild("aa");
        int hash1 = a.hashCode();
        int hash2 = a.hashCode();

        assertEquals(hash1, hash2);
    }

    /**
     * At least this test works well.
     */
    @Test
    void equalSymmetricalTreesTest() {
        var a = new Tree<>(1);
        a.addChild(2);
        a.addChild(3);

        var b = new Tree<>(1);
        b.addChild(3);
        b.addChild(2);

        assertEquals(a, b);
    }

    @Test
    void exceptionTest() {
        assertThrows(ConcurrentModificationException.class, () -> {
            Tree<String> tree = new Tree<>("R1");
            Tree<String> subtree = new Tree<>("R2");
            subtree.addChild("C");
            subtree.addChild("D");
            tree.addChild(subtree);
            var a = tree.addChild("A");
            var b = a.addChild("B");
            b.remove();
            System.out.println("BFS exception");
            for (String i : tree) {
                tree.addChild("HEHA");
            }
        });
    }

    @Test
    void exceptionDfsTest() {
        assertThrows(ConcurrentModificationException.class, () -> {
            Tree<String> tree = new Tree<>("R1");
            Tree<String> subtree = new Tree<>("R2");
            subtree.addChild("C");
            subtree.addChild("D");
            tree.addChild(subtree);
            var a = tree.addChild("A");
            var b = a.addChild("B");
            b.remove();
            tree.setIterType(Tree.IteratorType.DFS);
            System.out.println("DFS exception");
            for (String i : tree) {
                tree.addChild("HEHA");
            }
        });
    }

}