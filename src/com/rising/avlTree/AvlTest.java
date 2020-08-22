package com.rising.avlTree;

public class AvlTest {
    public static void main(String[] args) {
        int[] elements = {8, 5, 4, 6, 10, 9, 11, 22, 55, 23, 14, 1};
        //int[] elements = {8, 4, 9, 3, 5, 2};
        //int[] elements = {8, 10, 9};
        //int[] elements = {8, 5, 4};
        //int[] elements = {15, 8, 20, 4, 10, 25, 3, 5, 9, 11, 12};

        AvlService service = new AvlService();
        AvlNode root = null;
        System.out.println("二叉查找树");
        for (int element : elements) {
            root = service.insert(element, root, false);
        }
        service.printAvlTree(root);
        System.out.print("\n先序遍历：");
        service.printPre(root);
        System.out.print("\n中序遍历：");
        service.printCenter(root);
        System.out.print("\n后序遍历：");
        service.printPost(root);
        root = null;
        System.out.println("\n平衡二叉树");
        for (int element : elements) {
            root = service.insert(element, root, true);
        }
        service.printAvlTree(root);
        int e = 9;
        System.out.println("删除节点：" + e);
        root = service.remove(e, root);
        service.printAvlTree(root);
    }
}
