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

    public void insert(int k, String s) {
        RBTNode z = new RBTNode(k, s);
        z.left = nil;
        z.right = nil;
        z.parent = nil;
        // Neuer Knoten ist rot
        z.color = RBTNode.red;

        RBTNode y = nil;
        RBTNode x = root;

        // Finde Position zum Einfügen
        while (x != nil) {
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        z.parent = y;
        if (y == nil) {
            // z wird zur Wurzel, weil der Baum leer ist
            root = z;
        } else {
            if (z.key < y.key) {
                y.left = z;
            } else {
                y.right = z;
            }
        }

        z.left = nil;
        z.right = nil;
        z.color = RBTNode.red;
        rbInsertFixup(z);
    }

    public String search(int key) {
        RBTNode result = search(root, key);
        if(result != nil) {
            return result.val;
        }
        // Wenn nicht gefunden
        return null;
    }

    private RBTNode search(RBTNode node, int key) {
        if (node == nil || node.key == key) {
            return node;
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
        if (node == nil)
            return 0;
        int lHeight = height(node.left);
        int rHeight = height(node.right);
        return Math.max(lHeight, rHeight) + 1;
    }

     // claude.ai (Prompt: gib mir eine generelle Idee, wie ich überprüfen kann, ob ein schwarz roter baum ein schwarz roter baum ist)
    public boolean CheckRB() {
        return CheckRB(root) && checkBlackHeight();
    }

    private boolean CheckRB(RBTNode node) {
        if (node == root && isRed(node)) {
            return false;
        }

        if (node == nil) return true;

        // Prüfen, ob es rote Knoten mit roten Kindern gibt
        if (isRed(node) && (isRed(node.left) || isRed(node.right)))
            return false;

        // Rekursiv den linken und rechten Teilbaum überprüfen
        if (!CheckRB(node.left) || !CheckRB(node.right))
            return false;

        return true;
    }

    public boolean checkBlackHeight() {
        return getBlackHeight(root) != -1;
    }

    private int getBlackHeight(RBTNode node) {
        if (node == nil) {
            return 1;
        }

        int leftBlackHeight = getBlackHeight(node.left);
        int rightBlackHeight = getBlackHeight(node.right);

        if (leftBlackHeight == -1 || rightBlackHeight == -1 || leftBlackHeight != rightBlackHeight) {
            return -1;
        }

        int i = (node.color == RBTNode.black) ? 1 : 0;
        return leftBlackHeight + i;
    }

    // hilfsmethode zur bestimmung roter knoten
    private boolean isRed(RBTNode node) {
        if (node == nil) return false;
        return node.color == RBTNode.red;
    }

    private void leftRotate(RBTNode node) {
        // y wird rechter Kindknoten von x
        RBTNode y = node.right;
        // Verschiebe y's linkes Teilbaum nach rechts von x
        node.right = y.left;
        if (y.left != nil) {
            y.left.parent = node;
        }
        // y übernimmt x's Elternknoten
        y.parent = node.parent;
        if (node.parent == nil) {
            // y wird neue Wurzel
            root = y;
        } else {
            if (node == node.parent.left) {
                node.parent.left = y;
            } else {
                node.parent.right = y;
            }
        }
        y.left = node;
        node.parent = y;
    }

    private void rightRotate(RBTNode node) {
        // x wird linker Kindknoten von y
        RBTNode x = node.left;
        // Verschiebe x's rechten Teilbaum nach links von y
        node.left = x.right;
        if (x.right != nil) {
            x.right.parent = node;
        }
        // x übernimmt y's Elternknoten
        x.parent = node.parent;
        if (node.parent == nil) {
            // x wird neue Wurzel
            root = x;
        } else {
            if (node == node.parent.right) {
                node.parent.right = x;
            } else {
                node.parent.left = x;
            }
        }
        x.right = node;
        node.parent = x;
    }

    // kommentare mit claude.ai eingefügt
    private void rbInsertFixup(RBTNode node) {
        while (node.parent.color == RBTNode.red) {
            // Elternknoten ist linker Kindknoten
            if (node.parent == node.parent.parent.left) {
                RBTNode y = node.parent.parent.right;  // Onkel von z
                if (y.color == RBTNode.red) {
                    // Fall 1: Onkel rot → Recoloring
                    node.parent.color = RBTNode.black;
                    y.color = RBTNode.black;
                    node.parent.parent.color = RBTNode.red;
                    node = node.parent.parent;
                } else {
                    // Fall 2+3: Onkel schwarz → Rotation notwendig
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.color = RBTNode.black;
                    node.parent.parent.color = RBTNode.red;
                    rightRotate(node.parent.parent);
                }
            } else {
                // Elternknoten ist rechter Kindknoten (symmetrisch)
                RBTNode y = node.parent.parent.left;  // Onkel von z
                if (y.color == RBTNode.red) {
                    node.parent.color = RBTNode.black;
                    y.color = RBTNode.black;
                    node.parent.parent.color = RBTNode.red;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.color = RBTNode.black;
                    node.parent.parent.color = RBTNode.red;
                    leftRotate(node.parent.parent);
                }
            }
        }
        root.color = RBTNode.black;
    }

    public void hurtRBT() {
        root.color = RBTNode.red;
    }

    public void hurtRBTChangeColors(int key) {
        Boolean localNode = search(root, key).color;
        if (localNode) {
            search(root, key).color = RBTNode.black;
        } else {
            search(root, key).color = RBTNode.red;
        }
    }
}

