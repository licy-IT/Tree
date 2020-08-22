package com.rising.avlTree;

public class AvlTest2 {
    public static void main(String[] args) {
        AvlNode root = null;
        AvlService service = new AvlService();
        int[] elements = {8, 5, 4, 6, 10, 9, 11, 22, 55, 23, 14, 1};
        System.out.println("\n平衡二叉树");
        for (int element : elements) {
            root = service.insert(element, root, true);
        }
        service.printAvlTree(root);
        System.out.println("节点1和14的最近公共父节点是：" + service.parent(root, 1, 14));
    }
}
