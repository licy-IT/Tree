package com.rising.redBlackTree;

public class RedBlackTreeTest {
    public static void main(String[] args) {
        //int[] elements = {8, 5, 4, 6, 10, 9, 11, 22, 55, 23, 14, 1};
        //int[] elements = {8, 4, 9, 3, 5, 2};
        //int[] elements = {8, 10, 9};
        //int[] elements = {8, 5, 4};
        int[] elements = {15, 8, 20, 4, 10, 25, 3, 5, 9, 11, 12, 23, 13, 14};

        RedBlackTree redBlackTree = new RedBlackTree();
        System.out.println("\n二叉查找树");
        for (int element : elements) {
            redBlackTree.root = redBlackTree.insert(redBlackTree.root, element, false);
        }
        redBlackTree.printRedBlackTree(redBlackTree.root);
        redBlackTree.root = null;
        System.out.println("\n红黑树");
        for (int element : elements) {
            redBlackTree.root = redBlackTree.insert(redBlackTree.root, element, true);
        }
        redBlackTree.printRedBlackTree(redBlackTree.root);
        int element = 14;
        System.out.println("删除节点：" + element);
        redBlackTree.remove(element);
        redBlackTree.printRedBlackTree(redBlackTree.root);
        redBlackTree.remove(3);
        redBlackTree.remove(5);
        redBlackTree.remove(11);
        redBlackTree.remove(13);
        redBlackTree.remove(20);
        redBlackTree.remove(25);
        System.out.println("删除节点：3、5、11、13、20、25");
        redBlackTree.printRedBlackTree(redBlackTree.root);
        element = 4;
        System.out.println("删除节点：" + element);
        redBlackTree.remove(element);
        redBlackTree.printRedBlackTree(redBlackTree.root);
    }
}
