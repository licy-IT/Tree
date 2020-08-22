package com.rising.avlTree;

public class AvlNode {
    //节点
    public int element;
    //左子树
    public AvlNode left;
    //右子树
    public AvlNode right;
    //树高
    public int height;

    public AvlNode(int theElement) {
        this(theElement, null, null);
    }

    public AvlNode(int theElement, AvlNode lt, AvlNode rt) {
        this.element = theElement;
        this.left = lt;
        this.right = rt;
        this.height = 0;
    }

    @Override
    public String toString() {
        return "AvlNode{" +
                "element=" + element +
                '}';
    }
}
