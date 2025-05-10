/* Quellen:
 * https://www.geeksforgeeks.org/binary-search-tree-set-1-search-and-insertion/
 * https://www.geeksforgeeks.org/binary-search-tree-set-3-iterative-delete/
 * https://www.geeksforgeeks.org/insertion-in-binary-search-tree/
 * https://www.geeksforgeeks.org/check-given-binary-tree-follows-height-property-red-black-tree/
 * https://www.geeksforgeeks.org/insertion-in-red-black-tree/ */

public class RedBlackTree {
    private final RBTNode nil = new RBTNode(-1, "nil");
    private RBTNode root;

    public RedBlackTree() {
        this.root = nil;
        this.nil.color = RBTNode.black;
        this.nil.left = nil;
        this.nil.right = nil;
        this.nil.parent = nil;
    } // Konstruktor

    public void insert(RBTNode node){
        RBTNode yNode = nil;
        RBTNode xNode = root;

        while (xNode != nil) {
            yNode = xNode;
            if (node.key < xNode.key) {
                xNode = xNode.left;
            } else {
                xNode = xNode.right;
            }
        }
        node.parent = yNode;
        if (yNode == nil) {
            root = node;
        } else if (node.key < yNode.key) {
            yNode.left = node;
        } else {
            yNode.right = node;
        }
        node.left = nil;
        node.right = nil;
        node.color = RBTNode.red;
        rbInsertFixup(node);
    }

    public String search(int key) {
        return search(root, key);
    }

    private String search(RBTNode node, int key) {
        if (node == nil || node.key == key) {
            return node.val;
        }

        if (key < node.key) {
            return search(node.left, key);
        } else {
            return search(node.right, key);
        }
    }

    // Returns height which is the number of edges along the longest path from the root node down to the farthest leaf node.
    public int height() {
        return height(root);
    }

    private int height(RBTNode node) {
        if (node == null) {
            node = nil;
        }
        if (node == nil)
            return 0;
        int lHeight = height(node.left);
        int rHeight = height(node.right);
        return Math.max(lHeight, rHeight) + 1;
    }

    public boolean CheckRB() {
        if (root == nil) {
            return true;
        }

        if (root.color != RBTNode.black) {
            return false;
        }

        return CheckRB(root);
    }

    private boolean CheckRB(RBTNode node) {
        if (node == nil) return true;

        if (node.color == RBTNode.red) {
            if ((node.left != nil && node.left.color == RBTNode.red) || (node.right != nil && node.right.color == RBTNode.red)) {
                return false;
            }
        }

        return CheckRB(node.left) && CheckRB(node.right);
    }

    private void leftRotate(RBTNode xNode) {
        if (xNode.right == nil) {
            return;
        }

        RBTNode yNode = xNode.right;
        xNode.right = yNode.left;

        if (yNode.left != nil) {
            yNode.left.parent = xNode;
        }
        yNode.parent = xNode.parent;

        if (xNode.parent == nil) {
            root = yNode;
        } else if (xNode == xNode.parent.left) {
            xNode.parent.left = yNode;
        } else {
            xNode.parent.right = yNode;
        }
        yNode.left = xNode;
        xNode.parent = yNode;
    }

    private void rightRotate(RBTNode xNode) {
        if (xNode.left == nil) {
            return;
        }

        RBTNode yNode = xNode.left;
        xNode.left = yNode.right;

        if (yNode.right != nil) {
            yNode.right.parent = xNode;
        }
        yNode.parent = xNode.parent;

        if (xNode.parent == nil) {
            root = yNode;
        } else if (xNode == xNode.parent.right) {
            xNode.parent.right = yNode;
        } else {
            xNode.parent.left = yNode;
        }
        yNode.right = xNode;
        xNode.parent = yNode;
    }

    private void rbInsertFixup(RBTNode zNode) {
        if (zNode == nil || zNode == root) return;

        while (zNode != root && zNode.parent.color == RBTNode.red) {
            if (zNode.parent == zNode.parent.parent.left) {
                RBTNode yNode = zNode.parent.parent.right;
                if (yNode.color == RBTNode.red) {
                    zNode.parent.color = RBTNode.black;
                    yNode.color = RBTNode.black;
                    zNode.parent.parent.color = RBTNode.red;
                    zNode = zNode.parent.parent;
                } else {
                    if (zNode == zNode.parent.right) {
                        zNode = zNode.parent;
                        leftRotate(zNode);
                    }
                    zNode.parent.color = RBTNode.black;
                    zNode.parent.parent.color = RBTNode.red;
                    rightRotate(zNode.parent.parent);
                }
            } else {
                RBTNode yNode = zNode.parent.parent.left;
                if (yNode.color == RBTNode.red) {
                    zNode.parent.color = RBTNode.black;
                    yNode.color = RBTNode.black;
                    zNode.parent.parent.color = RBTNode.red;
                    zNode = zNode.parent.parent;
                } else {
                    if (zNode == zNode.parent.left) {
                        zNode = zNode.parent;
                        leftRotate(zNode);
                    }
                    zNode.parent.color = RBTNode.black;
                    zNode.parent.parent.color = RBTNode.red;
                    rightRotate(zNode.parent.parent);
                }
            }
        }
        root.color = RBTNode.black;
    }
}

