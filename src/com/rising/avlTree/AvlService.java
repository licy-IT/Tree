package com.rising.avlTree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AvlService {
    private static final int BALANCE_FACTOR = 1;

    public AvlNode insert(int element, AvlNode avlNode, boolean isBalance) {
        if (avlNode == null) {
            return new AvlNode(element, null, null);
        }
        int compareResult = element - avlNode.element;
        if (compareResult < 0) {
            avlNode.left = insert(element, avlNode.left, isBalance);
        } else if (compareResult > 0) {
            avlNode.right = insert(element, avlNode.right, isBalance);
        } else { //相等，do nothing
            ;
        }
        return isBalance ? balance(avlNode) : avlNode;
    }

    public void printAvl(AvlNode root) {
        printAvlWithLength(root, 150);
    }

    public void printAvlWithLength(AvlNode root, int length) {
        List<AvlNode> list1 = new LinkedList<AvlNode>();
        List<AvlNode> list2 = new LinkedList<AvlNode>();
        list1.add(root);
        int[] avls = generateAvls(new int[length], root, 0, length);
        avlPrintByRecurion(avls, list1, list2);
    }

    /**
     * 直接调用打印方法，用长度为100的数组来存放二叉树
     *
     * @param root
     */
    public void printAvlTree(AvlNode root) {
        printAvlTreeWithLength(root, 150);
    }

    /**
     * 指定数组长度来存放二叉树
     *
     * @param root
     * @param length
     */
    public void printAvlTreeWithLength(AvlNode root, int length) {
        List<AvlNode> list1 = new LinkedList<>();
        List<AvlNode> list2 = new LinkedList<>();
        list1.add(root);
        int[] avls = generateAvls(new int[length], root, 0, length);
        Map<String, String> flagMap = new HashMap<>();
        flagMap.put("flag", "N");
        printAvlByRecurion(avls, list1, list2, flagMap);
    }

    /**
     * 递归打印方法
     *
     * @param avls
     * @param list1
     * @param list2
     * @param flagMap
     */
    private void printAvlByRecurion(int[] avls, List<AvlNode> list1, List<AvlNode> list2, Map<String, String> flagMap) {
        if (list1.isEmpty() && list2.isEmpty()) {
            return;
        }
        if (!list1.isEmpty()) {
            String[] printAvls = new String[avls.length];
            while (!list1.isEmpty()) {
                AvlNode avlNode = list1.remove(0);
                //将父节点放入到打印数组中
                int parentIndex = getIndex(avlNode, avls);
                printAvls[parentIndex] = String.valueOf(avlNode.element);
                if (avlNode.left != null) {
                    list2.add(avlNode.left);
                    int leftIndex = getIndex(avlNode.left, avls);
                    generatePrivateAvls(leftIndex, parentIndex - 1, printAvls);
                }
                if (avlNode.right != null) {
                    list2.add(avlNode.right);
                    int rightIndex = getIndex(avlNode.right, avls);
                    generatePrivateAvls(parentIndex + 1, rightIndex, printAvls);
                }
            }
            printAvlsByString(printAvls, flagMap);
            printAvlByRecurion(avls, list1, list2, flagMap);
        }
        if (!list2.isEmpty()) {
            String[] printAvls = new String[avls.length];
            while (!list2.isEmpty()) {
                AvlNode avlNode = list2.remove(0);
                //将父节点放入到打印数组中
                int parentIndex = getIndex(avlNode, avls);
                printAvls[parentIndex] = String.valueOf(avlNode.element);
                if (avlNode.left != null) {
                    list1.add(avlNode.left);
                    int leftIndex = getIndex(avlNode.left, avls);
                    generatePrivateAvls(leftIndex, parentIndex - 1, printAvls);
                }
                if (avlNode.right != null) {
                    list1.add(avlNode.right);
                    int rightIndex = getIndex(avlNode.right, avls);
                    generatePrivateAvls(parentIndex + 1, rightIndex, printAvls);
                }
            }
            printAvlsByString(printAvls, flagMap);
            printAvlByRecurion(avls, list1, list2, flagMap);
        }
    }

    /**
     * 将构建好的数组打印
     *
     * @param avls
     * @param flagMap
     */
    private void printAvlsByString(String[] avls, Map<String, String> flagMap) {
        for (int i = 0; i < avls.length; i++) {
            if (avls[i] != null && !avls[i].equals("-")) {
                if ("Y".equals(flagMap.get("flag"))) {
                    System.out.print("|");
                }
            } else {
                System.out.print(" ");
            }
        }
        flagMap.put("flag", "Y");
        System.out.println();
        for (int i = 0; i < avls.length; i++) {
            if (avls[i] != null) {
                System.out.print(avls[i]);
            } else {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    /**
     * 用于构建左右子树的连接
     *
     * @param startIndex
     * @param endIndex
     * @param avls
     */
    private void generatePrivateAvls(int startIndex, int endIndex, String[] avls) {
        for (int i = startIndex; i <= endIndex; i++) {
            avls[i] = "-";
        }
    }

    /**
     * 获取当前元素在数组里的索引
     *
     * @param avlNode
     * @param avls
     * @return
     */
    private int getIndex(AvlNode avlNode, int[] avls) {
        for (int i = 0; i < avls.length; i++) {
            if (avlNode.element == avls[i]) {
                return i;
            }
        }
        return -1;
    }

    private void avlPrintByRecurion(int[] avls, List<AvlNode> list1, List<AvlNode> list2) {
        if (list1.isEmpty() && list2.isEmpty()) {
            return;
        }
        if (!list1.isEmpty()) {
            avlPrintByList(list1, avls);
            while (!list1.isEmpty()) {
                AvlNode avlNode = list1.remove(0);
                if (avlNode.left != null) {
                    list2.add(avlNode.left);
                }
                if (avlNode.right != null) {
                    list2.add(avlNode.right);
                }
            }
            avlPrintByRecurion(avls, list1, list2);
        }
        if (!list2.isEmpty()) {
            avlPrintByList(list2, avls);
            while (!list2.isEmpty()) {
                AvlNode avlNode = list2.remove(0);
                if (avlNode.left != null) {
                    list1.add(avlNode.left);
                }
                if (avlNode.right != null) {
                    list1.add(avlNode.right);
                }
            }
            avlPrintByRecurion(avls, list1, list2);
        }
    }

    /**
     * 将每一层的数据放在list中，并打印
     *
     * @param list
     * @param avls
     */
    private void avlPrintByList(List<AvlNode> list, int[] avls) {
        int[] temps = new int[avls.length];
        for (int i = 0; i < avls.length; i++) {
            for (AvlNode avlNode : list) {
                if (avlNode.element == avls[i]) {
                    temps[i] = avlNode.element;
                }
            }
        }
        for (int element : temps) {
            if (element != 0) {
                System.out.print(element);
            } else {
                System.out.print("-");
            }
        }
        System.out.println();
    }

    /**
     * 将二叉树放在一维数组
     *
     * @param avls
     * @param avlNode
     * @param startLocation
     * @param endLocation
     * @return
     */
    public int[] generateAvls(int[] avls, AvlNode avlNode, int startLocation, int endLocation) {
        if (avlNode == null) {
            return avls;
        }
        if (endLocation - startLocation < 2) {
            System.out.println("区间不够，请扩容");
        }
        int flag = (endLocation - startLocation) % 2;
        int rootLocation = (endLocation - startLocation) / 2 + startLocation;
        if (flag > 0) {
            rootLocation = rootLocation + 1;
        }
        avls[rootLocation] = avlNode.element;
        if (avlNode.left != null) {
            generateAvls(avls, avlNode.left, startLocation, rootLocation - 1);
        }
        if (avlNode.right != null) {
            generateAvls(avls, avlNode.right, rootLocation + 1, endLocation);
        }
        return avls;
    }

    public int getHeight(AvlNode avlNode) {
        if (avlNode == null) {
            return -1;
        }
        return avlNode.height;
    }

    /**
     * 左左情况，右单旋
     *
     * @param avlNode
     * @return
     */
    private AvlNode leftLeftRotate(AvlNode avlNode) {
        AvlNode left = avlNode.left;
        avlNode.left = left.right;
        left.right = avlNode;
        avlNode.height = Math.max(getHeight(avlNode.left), getHeight(avlNode.right)) + 1;
        left.height = Math.max(getHeight(left.left), getHeight(left.right)) + 1;
        return left;
    }

    /**
     * 右右旋转，左单旋
     *
     * @param avlNode
     * @return
     */
    private AvlNode rightRightRotate(AvlNode avlNode) {
        AvlNode right = avlNode.right;
        avlNode.right = right.left;
        right.left = avlNode;
        avlNode.height = Math.max(getHeight(avlNode.left), getHeight(avlNode.right)) + 1;
        right.height = Math.max(getHeight(right.left), getHeight(right.right)) + 1;
        return right;
    }

    /**
     * 左右情况，先用左子树进行左单旋，自己再进行右单旋
     *
     * @param avlNode
     * @return
     */
    private AvlNode leftRightRotate(AvlNode avlNode) {
        avlNode.left = rightRightRotate(avlNode.left);
        return leftLeftRotate(avlNode);
    }

    /**
     * 右左情况，先用右子树进行右单旋，自己再进行左单旋
     *
     * @param avlNode
     * @return
     */
    private AvlNode rightLeftRotate(AvlNode avlNode) {
        avlNode.right = leftLeftRotate(avlNode.right);
        return rightRightRotate(avlNode);
    }

    /**
     * 平衡二叉树方法
     *
     * @param avlNode
     * @return
     */
    private AvlNode balance(AvlNode avlNode) {
        if (avlNode == null) {
            return avlNode;
        }
        if (getHeight(avlNode.left) - getHeight(avlNode.right) > BALANCE_FACTOR) {
            if (getHeight(avlNode.left.left) >= getHeight(avlNode.left.right)) {
                avlNode = leftLeftRotate(avlNode);
            } else {
                avlNode = leftRightRotate(avlNode);
            }
        } else if (getHeight(avlNode.right) - getHeight(avlNode.left) > 1) {
            if (getHeight(avlNode.right.right) >= getHeight(avlNode.right.left)) {
                avlNode = rightRightRotate(avlNode);
            } else {
                avlNode = rightLeftRotate(avlNode);
            }
        }
        avlNode.height = Math.max(getHeight(avlNode.left), getHeight(avlNode.right)) + 1;
        return avlNode;
    }

    /**
     * @param element
     * @param avlNode
     * @return
     */
    public AvlNode remove(int element, AvlNode avlNode) {
        if (avlNode == null) {
            return avlNode;
        }
        int compareResult = element - avlNode.element;
        if (compareResult < 0) {
            avlNode.left = remove(element, avlNode.left);
        } else if (compareResult > 0) {
            avlNode.right = remove(element, avlNode.right);
        } else if (avlNode.left != null && avlNode.right != null) {
            avlNode.element = findMin(avlNode.right).element;
            avlNode.right = remove(avlNode.element, avlNode.right);
        } else {
            avlNode = avlNode.left != null ? avlNode.left : avlNode.right;
        }
        return balance(avlNode);
    }

    /**
     * 找到最小节点
     *
     * @param avlNode
     * @return
     */
    private AvlNode findMin(AvlNode avlNode) {
        if (avlNode == null) {
            return null;
        } else if (avlNode.left == null) {
            return avlNode;
        }
        return findMin(avlNode.left);
    }

    private AvlNode findMax(AvlNode avlNode) {
        if (avlNode != null) {
            while (avlNode.right != null) {
                avlNode = avlNode.right;
            }
        }
        return avlNode;
    }

    /**
     * 先序遍历
     *
     * @param avlNode
     */
    public void printPre(AvlNode avlNode) {
        if (avlNode != null) {
            System.out.print(avlNode.element + " ");
            printPre(avlNode.left);
            printPre(avlNode.right);
        }
    }

    /**
     * 中序遍历
     *
     * @param avlNode
     */
    public void printCenter(AvlNode avlNode) {
        if (avlNode != null) {
            printCenter(avlNode.left);
            System.out.print(avlNode.element + " ");
            printCenter(avlNode.right);
        }
    }

    /**
     * 后序遍历
     *
     * @param avlNode
     */
    public void printPost(AvlNode avlNode) {
        if (avlNode != null) {
            printPost(avlNode.left);
            printPost(avlNode.right);
            System.out.print(avlNode.element + " ");
        }
    }

    /**
     *
     * @param node
     * @param a
     * @param b
     */
    public AvlNode parent(AvlNode node, int a, int b) {
        List alist = new LinkedList();
        List blist = new LinkedList();
        List alistTemp = new LinkedList();
        List blistTemp = new LinkedList();
        findParent(node, a, alistTemp);
        findParent(node, b, blistTemp);
        for (int i = 0; i < alistTemp.size(); i++) {
            alist.add(0, alistTemp.get(i));
        }
        for (int i = 0; i < blistTemp.size(); i++) {
            blist.add(0, blistTemp.get(i));
        }

        int length = alist.size() > blist.size() ? blist.size() : alist.size();
        for (int i = 0; i < length; i++) {
            if (!alist.get(i).equals(blist.get(i))) {
                int elemet = (int) alist.get(i - 1);
                return new AvlNode(elemet);
            }
        }
        return null;
    }

    public AvlNode findParent(AvlNode node,int element, List list) {
        if (node == null) {
            return node;
        }
        if (node.element == element) {
            list.add(node.element);
            return node;
        }

        AvlNode left = findParent(node.left, element, list);
        AvlNode right = findParent(node.right, element, list);
        if (left != null) {
            list.add(node.element);
            return left;
        }
        if (right != null) {
            list.add(node.element);
            return right;
        }
        return null;
    }

}
