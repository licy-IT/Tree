package com.rising.redBlackTree;

public class RedBlackTreeTest2 {
    public static void main(String[] args) {
        int[] elements = {8, 5, 4};

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
        int element = 4;
        System.out.println("删除节点：" + element);
        redBlackTree.remove(element);
        redBlackTree.printRedBlackTree(redBlackTree.root);
        element = 5;
        System.out.println("删除节点：" + element);
        redBlackTree.remove(element);
        redBlackTree.printRedBlackTree(redBlackTree.root);
        element = 8;
        System.out.println("删除节点：" + element);
        redBlackTree.remove(element);
        redBlackTree.printRedBlackTree(redBlackTree.root);
    }
}
