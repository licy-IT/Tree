package com.rising.redBlackTree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RedBlackTree {
    private static final int BLACK = 1;
    private static final int RED = 0;

    public RedBlackNode root;

    private RedBlackNode current;
    private RedBlackNode parent;
    private RedBlackNode grand;
    private RedBlackNode uncle;
    private RedBlackNode brother;

    public RedBlackTree() {
    }

    private static class RedBlackNode {
        public RedBlackNode left;
        public RedBlackNode right;
        public RedBlackNode parent;
        public int element;
        public int color;

        public RedBlackNode(int element) {
            this(element, null, null, null);
        }

        public RedBlackNode(int element, RedBlackNode left, RedBlackNode right, RedBlackNode parent) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.color = RED;
        }

        @Override
        public String toString() {
            if (color == RED) {
                return element + "R";
            } else {
                return element + "B";
            }
        }
    }

    /**
     * 右单旋
     *
     * @param redBlackNode
     * @return
     */
    private RedBlackNode rightRotate(RedBlackNode redBlackNode) {
        RedBlackNode left = redBlackNode.left;
        redBlackNode.left = left.right;
        left.right = redBlackNode;
        //交换父子节点关系
        left.parent = redBlackNode.parent;
        redBlackNode.parent = left;
        if (redBlackNode.left != null) {
            redBlackNode.left.parent = redBlackNode;
        }
        return left;
    }

    /**
     * 左单旋
     *
     * @param redBlackNode
     * @return
     */
    private RedBlackNode leftRotate(RedBlackNode redBlackNode) {
        RedBlackNode right = redBlackNode.right;
        redBlackNode.right = right.left;
        right.left = redBlackNode;
        //交换父子节点关系
        right.parent = redBlackNode.parent;
        redBlackNode.parent = right;
        if (redBlackNode.right != null) {
            redBlackNode.right.parent = redBlackNode;
        }
        return right;
    }

    /**
     * 获取节点颜色
     *
     * @param redBlackNode
     * @return
     */
    private int getColor(RedBlackNode redBlackNode) {
        if (redBlackNode == null) {
            return BLACK;
        }
        return redBlackNode.color;
    }

    /**
     * 红黑树的插入，isBalance=true则构建红黑树，否则构建普通二叉树
     *
     * @param redBlackNode
     * @param element
     * @param isBalance
     * @return
     */
    public RedBlackNode insert(RedBlackNode redBlackNode, int element, boolean isBalance) {
        root = insertElement(redBlackNode, element);
        if (isBalance == true) {
            balance(current);
        }
        return root;
    }

    /**
     * 插入方法
     *
     * @param redBlackNode
     * @param element
     * @return
     */
    public RedBlackNode insertElement(RedBlackNode redBlackNode, int element) {
        if (redBlackNode == null) {
            current = new RedBlackNode(element);
            return current;
        }
        int compareResult = redBlackNode.element - element;
        if (compareResult > 0) {
            redBlackNode.left = insertElement(redBlackNode.left, element);
            redBlackNode.left.parent = redBlackNode;
        } else if (compareResult < 0) {
            redBlackNode.right = insertElement(redBlackNode.right, element);
            redBlackNode.right.parent = redBlackNode;
        } else { //元素相等，do nothing
            ;
        }
        return redBlackNode;
    }

    /**
     * 平衡方法
     *
     * @param redBlackNode
     */
    private void balance(RedBlackNode redBlackNode) {
        if (redBlackNode.parent == null) {
            //第一种情况，当前节点是根节点，直接将颜色翻转成黑色
            redBlackNode.color = BLACK;
        } else if (getColor(redBlackNode.parent) == BLACK) {
            //第二种情况，当前节点的父节点是黑色
        } else if (getColor(redBlackNode.parent) == RED) {
            //首先将当前节点、父节点、祖父节点、叔叔节点构建出来
            current = redBlackNode;
            parent = redBlackNode.parent;
            grand = parent.parent;
            if (parent == grand.left) {
                //第三种到第五种情况是父节点在左侧，第六种到第八种是父节点在右侧，属于镜面对称
                uncle = grand.right;
                if (getColor(uncle) == RED) {
                    //第三种情况，当前节点的父节点是红色，叔叔节点也是红色
                    //(01) 将父节点设为黑色。
                    //(02) 将叔叔节点设为黑色。
                    //(03) 将祖父节点设为红色。
                    //(04) 将祖父节点设为当前节点(红色节点)；之后继续对当前节点进行操作。
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grand.color = RED;
                    current = grand;
                    balance(current);
                } else {
                    if (current == parent.right) {
                        //第四种情况，当前节点的父节点是红色，叔叔节点是黑色，且当前节点是其父节点的右孩子
                        //(01) 将父节点作为新的当前节点。
                        //(02) 以新的当前节点为支点进行左旋。
                        //(03) 将祖父节点的左节点指向旋转后的节点
                        current = parent;
                        grand.left = leftRotate(current);
                        //左旋后，变成第五种情况，此时祖父节点不变，子节点变为父节点，父节点变为子节点
                        balance(current);
                    } else {
                        //第五种情况，当前节点的父节点是红色，叔叔节点是黑色，且当前节点是其父节点的左孩子
                        //(01) 将父节点设为黑色。
                        //(02) 将祖父节点设为红色。
                        //(03) 以祖父节点为支点进行右旋。
                        //(04) 需要将祖父节点指向旋转之后的节点，若祖父点没有父节点，则新节点为root。
                        parent.color = BLACK;
                        grand.color = RED;
                        current = grand;
                        if (grand.parent != null) {
                            if (grand.parent.left == grand) {
                                grand.parent.left = rightRotate(current);
                            } else {
                                grand.parent.right = rightRotate(current);
                            }
                        } else {
                            root = rightRotate(current);
                        }
                    }
                }
            } else {
                //第三种到第五种情况是父节点在左侧，第六种到第八种是父节点在右侧，属于镜面对称
                uncle = grand.left;
                if (getColor(uncle) == RED) {
                    //第六种情况（等同于第三种情况），当前节点的父节点是红色，叔叔节点也是红色
                    //(01) 将父节点设为黑色。
                    //(02) 将叔叔节点设为黑色。
                    //(03) 将祖父节点设为红色。
                    //(04) 将祖父节点设为当前节点(红色节点)；之后继续对当前节点进行操作。
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grand.color = RED;
                    current = grand;
                    balance(current);
                } else {
                    if (current == parent.right) {
                        //第七种情况（和第五种情况对称，区别是LL进行右旋，RR进行左旋），当前节点的父节点是红色，叔叔节点是黑色，且当前节点是其父节点的右孩子
                        //(01) 将父节点设为黑色。
                        //(02) 将祖父节点设为红色。
                        //(03) 以祖父节点为支点进行左旋。
                        //(04) 需要将祖父节点指向旋转之后的节点，若祖父点没有父节点，则新节点为root。
                        parent.color = BLACK;
                        grand.color = RED;
                        current = grand;
                        if (grand.parent != null) {
                            if (grand.parent.left == grand) {
                                grand.parent.left = leftRotate(current);
                            } else {
                                grand.parent.right = leftRotate(current);
                            }
                        } else {
                            root = leftRotate(current);
                        }
                    } else {
                        //第八种情况，当前节点的父节点是红色，叔叔节点是黑色，且当前节点是其父节点的左孩子
                        //(01) 将父节点作为新的当前节点。
                        //(02) 以新的当前节点为支点进行右旋。
                        //(03) 将祖父节点的右节点指向旋转后的节点
                        current = parent;
                        grand.right = rightRotate(current);
                        //右旋后，变成第七种情况，此时祖父节点不变，子节点变为父节点，父节点变为子节点
                        balance(current);
                    }
                }
            }
        }
    }

    public void remove(int element) {
        removeForBalance(element, root, true);
        deleteElement(element, root);
    }

    public void removeForBalance(int element, RedBlackNode redBlackNode, boolean isRemove) {
        if (redBlackNode == null) {
            return;
        }
        if (element == root.element && root.left == null && root.right == null) {
            root = null;
            return;
        }
        int compareResult = element - redBlackNode.element;
        if (compareResult < 0) {
            removeForBalance(element, redBlackNode.left, isRemove);
        } else if (compareResult > 0) {
            removeForBalance(element, redBlackNode.right, isRemove);
        } else if (redBlackNode.left != null && redBlackNode.right != null && isRemove) {
            //当要删除的节点左右节点都不为空，则找到他的直接后继节点，来替换当前节点，最后再删除后继节点
            redBlackNode.element = findMin(redBlackNode.right).element;
            removeForBalance(redBlackNode.element, redBlackNode.right, isRemove);
        } else {
            parent = redBlackNode.parent;
            //已经找到需要删除的节点，接下来进行删除，并恢复红黑树，假设待删除节点为D
            //D为红节点只有一种情况；D为红节点，并且D为叶子节点
            if (getColor(redBlackNode) == RED && redBlackNode.left == null && redBlackNode.right == null) {
                return;
            }
            //D为黑节点，分为两种：1、D左节点或右节点为空，此时另一个节点必然为红节点。2、D为叶子节点
            if (redBlackNode.left != null && isRemove) {
                redBlackNode.element = redBlackNode.left.element;
                redBlackNode.left = null;
                return;
            }
            if (redBlackNode.right != null && isRemove) {
                redBlackNode.element = redBlackNode.right.element;
                redBlackNode.right = null;
                return;
            }
            //下面讨论最复杂的情况：D为叶子节点，（每种情况都包含镜面对称情况）
            if (redBlackNode.parent == null) {
                redBlackNode.color = BLACK;
                return;
            }
            if (redBlackNode == redBlackNode.parent.left) {
                brother = parent.right;
                if (getColor(brother) == RED) {
                    //第一种情况
                    removeByBrotherIsRed(true);
                    removeForBalance(redBlackNode.element, root, isRemove);
                } else if (getColor(brother) == BLACK && getColor(brother.right) == RED) {
                    //第二种情况
                    removeByRemoteBrotherSon(true);
                } else if (getColor(brother) == BLACK && getColor(brother.left) == RED) {
                    //第三种情况
                    removeByCloseBrotherSon(true);
                } else if (getColor(parent) == RED && getColor(brother) == BLACK && getColor(brother.left) == BLACK && getColor(brother.right) == BLACK) {
                    if (brother.left == null && brother.right == null) {
                        //第四种情况
                        //父节点为红色，兄弟节点和两个侄子节点都是黑色，且两个侄子为空
                        parent.color = BLACK;
                        brother.color = RED;
                    } else if (brother.left != null && brother.right != null) {
                        //第五种情况
                        //父节点为红色，兄弟节点和两个侄子节点都是黑色，且两个侄子不为空
                        //父节点左单旋
                        leftRotate(parent);
                    }
                } else if (getColor(parent) == BLACK && getColor(brother) == BLACK && getColor(brother.left) == BLACK && getColor(brother.right) == BLACK) {
                    //第六种情况
                    brother.color = RED;
                    removeForBalance(parent.element, root, false);
                }
            }
            if (redBlackNode == redBlackNode.parent.right) {
                brother = parent.left;
                if (getColor(brother) == RED) {
                    //第一种情况 的镜面对称情况
                    removeByBrotherIsRed(false);
                    removeForBalance(redBlackNode.element, root, isRemove);
                } else if (getColor(brother) == BLACK && getColor(brother.left) == RED) {
                    //第二种情况 的镜面对称情况
                    removeByRemoteBrotherSon(false);
                } else if (getColor(brother) == BLACK && getColor(brother.right) == RED) {
                    //第三种情况 的镜面对称情况
                    removeByCloseBrotherSon(false);
                } else if (getColor(parent) == RED && getColor(brother) == BLACK && getColor(brother.left) == BLACK && getColor(brother.right) == BLACK) {
                    if (brother.left == null && brother.right == null) {
                        //第四种情况 的镜面对称情况
                        //父节点为红色，兄弟节点和两个侄子节点都是黑色，且两个侄子为空
                        parent.color = BLACK;
                        brother.color = RED;
                    } else if (brother.left != null && brother.right != null) {
                        //第五种情况 的镜面对称情况
                        //父节点为红色，兄弟节点和两个侄子节点都是黑色，且两个侄子不为空
                        //父节点右单旋
                        rightRotate(parent);
                    }
                } else if (getColor(parent) == BLACK && getColor(brother) == BLACK && getColor(brother.left) == BLACK && getColor(brother.right) == BLACK) {
                    //第六种情况
                    brother.color = RED;
                    removeForBalance(parent.element, root, false);
                }
            }
        }
        return;
    }

    /**
     * 第一种情况：D的兄弟节点为红色。
     * 思路：父节点和兄弟节点颜色互换，并且以父节点为当前节点进行旋转，变成情况四
     * 当祖父节点存在，则让祖父节点指向新的父节点，否则，旋转后的节点为root。
     * 此情况存在镜面对称情况，用isLeft区分，
     * 当前节点是父节点的左节点，则isLeft为true，进行左单旋，否则进行右单旋。
     *
     * @param isLeft
     */
    private void removeByBrotherIsRed(boolean isLeft) {
        int parentColor = getColor(parent);
        parent.color = getColor(brother);
        brother.color = parentColor;
        if (parent.parent != null) {
            grand = parent.parent;
            if (grand.left == parent) {
                grand.left = isLeft ? leftRotate(parent) : rightRotate(parent);
            } else {
                grand.right = isLeft ? leftRotate(parent) : rightRotate(parent);
            }
        } else {
            root = isLeft ? leftRotate(parent) : rightRotate(parent);
        }
    }

    /**
     * 第二种情况：D的远侄子为红色
     * 思路，删除后右子树比左子树多一个黑节点，想办法让红节点变成黑节点，并让右子树两个黑节点分布到两个子树
     * 1、父节点和兄弟节点互换颜色
     * 2、远侄子变成黑色
     * 3、对父节点进行RR操作（左单旋）
     * isLeft为false则就是镜面对称情况，进行右单旋
     *
     * @param isLeft
     */
    private void removeByRemoteBrotherSon(boolean isLeft) {
        int parentColor = parent.color;
        parent.color = brother.color;
        brother.color = parentColor;
        if (isLeft) {
            //parent.left = null; //删除D
            brother.right.color = BLACK;
        } else {
            //parent.right = null; //删除D
            brother.left.color = BLACK;
        }
        if (parent.parent != null) {
            grand = parent.parent;
            if (grand.left == parent) {
                grand.left = isLeft ? leftRotate(parent) : rightRotate(parent);
            } else {
                grand.right = isLeft ? leftRotate(parent) : rightRotate(parent);
            }
        } else {
            root = isLeft ? leftRotate(parent) : rightRotate(parent);
        }
    }

    /**
     * 第三种情况：D的近侄子为红色
     * 1、将兄弟节点和近侄子颜色互换
     * 2、将兄弟节点右单旋，(此时兄弟节点和侄子角色互换)然后就变成了第二种情况
     * 3、按照第二种情况进行删除
     * isLeft为false则就是镜面对称情况，进行左单旋
     *
     * @param isLeft
     */
    private void removeByCloseBrotherSon(boolean isLeft) {
        int brotherColor = getColor(brother);
        brother.color = getColor(brother.left);
        if (isLeft) {
            brother.left.color = brotherColor;
        } else {
            brother.right.color = brotherColor;
        }
        if (isLeft) {
            parent.right = rightRotate(brother);
            brother = parent.right;
        } else {
            parent.left = leftRotate(brother);
            brother = parent.left;
        }
        removeByRemoteBrotherSon(isLeft);
    }

    private void deleteElement(int element, RedBlackNode redBlackNode) {
        if (redBlackNode == null) {
            return;
        }
        if (element == root.element && root.left == null && root.right == null) {
            root = null;
            return;
        }
        int compareResult = element - redBlackNode.element;
        if (compareResult < 0) {
            deleteElement(element, redBlackNode.left);
        } else if (compareResult > 0) {
            deleteElement(element, redBlackNode.right);
        } else {
            //找到删除节点直接删除
            parent = redBlackNode.parent;
            if (redBlackNode == parent.left) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
    }

    /**
     * 找到最小节点
     *
     * @param redBlackNode
     * @return
     */
    private RedBlackNode findMin(RedBlackNode redBlackNode) {
        if (redBlackNode == null) {
            return null;
        } else if (redBlackNode.left == null) {
            return redBlackNode;
        }
        return findMin(redBlackNode.left);
    }


    /**
     * 直接调用打印方法，用长度为100的数组来存放二叉树
     *
     * @param root
     */
    public void printRedBlackTree(RedBlackNode root) {
        printRedBlackTreeWithLength(root, 150);
    }

    /**
     * 指定数组长度来存放二叉树
     *
     * @param root
     * @param length
     */
    public void printRedBlackTreeWithLength(RedBlackNode root, int length) {
        if (root == null) {
            return;
        }
        List<RedBlackNode> list1 = new LinkedList<>();
        List<RedBlackNode> list2 = new LinkedList<>();
        list1.add(root);
        RedBlackNode[] rbs = generateRbs(new RedBlackNode[length], root, 0, length);
        Map<String, String> flagMap = new HashMap<>();
        flagMap.put("flag", "N");
        printRedBlackByRecurion(rbs, list1, list2, flagMap);
    }

    /**
     * 递归打印方法
     *
     * @param rbs
     * @param list1
     * @param list2
     * @param flagMap
     */
    private void printRedBlackByRecurion(RedBlackNode[] rbs, List<RedBlackNode> list1, List<RedBlackNode> list2, Map<String, String> flagMap) {
        if (list1.isEmpty() && list2.isEmpty()) {
            return;
        }
        if (!list1.isEmpty()) {
            String[] printRbs = new String[rbs.length];
            while (!list1.isEmpty()) {
                RedBlackNode redBlackNode = list1.remove(0);
                //将父节点放入到打印数组中
                int parentIndex = getIndex(redBlackNode, rbs);
                printRbs[parentIndex] = redBlackNode.toString();
                if (redBlackNode.left != null) {
                    list2.add(redBlackNode.left);
                    int leftIndex = getIndex(redBlackNode.left, rbs);
                    generatePrivateRbs(leftIndex, parentIndex - 1, printRbs);
                }
                if (redBlackNode.right != null) {
                    list2.add(redBlackNode.right);
                    int rightIndex = getIndex(redBlackNode.right, rbs);
                    generatePrivateRbs(parentIndex + 1, rightIndex, printRbs);
                }
            }
            printRbsByString(printRbs, flagMap);
            printRedBlackByRecurion(rbs, list1, list2, flagMap);
        }
        if (!list2.isEmpty()) {
            String[] printRbs = new String[rbs.length];
            while (!list2.isEmpty()) {
                RedBlackNode redBlackNode = list2.remove(0);
                //将父节点放入到打印数组中
                int parentIndex = getIndex(redBlackNode, rbs);
                printRbs[parentIndex] = redBlackNode.toString();
                if (redBlackNode.left != null) {
                    list1.add(redBlackNode.left);
                    int leftIndex = getIndex(redBlackNode.left, rbs);
                    generatePrivateRbs(leftIndex, parentIndex - 1, printRbs);
                }
                if (redBlackNode.right != null) {
                    list1.add(redBlackNode.right);
                    int rightIndex = getIndex(redBlackNode.right, rbs);
                    generatePrivateRbs(parentIndex + 1, rightIndex, printRbs);
                }
            }
            printRbsByString(printRbs, flagMap);
            printRedBlackByRecurion(rbs, list1, list2, flagMap);
        }
    }

    /**
     * 将构建好的数组打印
     *
     * @param rbs
     * @param flagMap
     */
    private void printRbsByString(String[] rbs, Map<String, String> flagMap) {
        for (int i = 0; i < rbs.length; i++) {
            if (rbs[i] != null && !rbs[i].equals("-")) {
                if ("Y".equals(flagMap.get("flag"))) {
                    System.out.print("|");
                }
            } else {
                System.out.print(" ");
            }
        }
        flagMap.put("flag", "Y");
        System.out.println();
        for (int i = 0; i < rbs.length; i++) {
            if (rbs[i] != null) {
                System.out.print(rbs[i]);
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
     * @param rbs
     */
    private void generatePrivateRbs(int startIndex, int endIndex, String[] rbs) {
        for (int i = startIndex; i <= endIndex; i++) {
            rbs[i] = "-";
        }
    }

    /**
     * 获取当前元素在数组里的索引
     *
     * @param redBlackNode
     * @param rbs
     * @return
     */
    private int getIndex(RedBlackNode redBlackNode, RedBlackNode[] rbs) {
        for (int i = 0; i < rbs.length; i++) {
            if (rbs[i] != null && redBlackNode.element == rbs[i].element) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 将二叉树放在一维数组
     *
     * @param rbs
     * @param redBlackNode
     * @param startLocation
     * @param endLocation
     * @return
     */
    public RedBlackNode[] generateRbs(RedBlackNode[] rbs, RedBlackNode redBlackNode, int startLocation, int endLocation) {
        if (redBlackNode == null) {
            return rbs;
        }
        if (endLocation - startLocation < 2) {
            System.out.println("区间不够，请扩容");
        }
        int flag = (endLocation - startLocation) % 2;
        int rootLocation = (endLocation - startLocation) / 2 + startLocation;
        if (flag > 0) {
            rootLocation = rootLocation + 1;
        }
        rbs[rootLocation] = redBlackNode;
        if (redBlackNode.left != null) {
            generateRbs(rbs, redBlackNode.left, startLocation, rootLocation - 1);
        }
        if (redBlackNode.right != null) {
            generateRbs(rbs, redBlackNode.right, rootLocation + 1, endLocation);
        }
        return rbs;
    }
}
