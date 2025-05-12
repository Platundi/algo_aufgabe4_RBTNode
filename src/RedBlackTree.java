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
        return CheckRB(root);
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

        return checkBlackHeight();
    }

    // hilfsmethode zur bestimmung roter knoten
    private boolean isRed(RBTNode node) {
        if (node == nil) return false;
        return node.color == RBTNode.red;
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

    // kommentare mit claude.ai eingefügt
    private void rbInsertFixup(RBTNode zNode) {
        if (zNode == nil || zNode == root) return;

        while (zNode != root && zNode.parent.color == RBTNode.red) {
            if (zNode.parent == zNode.parent.parent.left) {
                // Der Elternknoten ist der linke Kindknoten des Großelternknotens
                RBTNode yNode = zNode.parent.parent.right;
                if (yNode.color == RBTNode.red) {
                    // Fall 1: Der Onkelknoten ist rot
                    zNode.parent.color = RBTNode.black;
                    yNode.color = RBTNode.black;
                    zNode.parent.parent.color = RBTNode.red;
                    zNode = zNode.parent.parent;
                } else {
                    // Der Onkelknoten ist schwarz
                    if (zNode == zNode.parent.right) {
                        // Fall 2: zNode ist rechtes Kind
                        zNode = zNode.parent;
                        leftRotate(zNode);
                    }
                    // Fall 3: zNode ist linkes Kind
                    zNode.parent.color = RBTNode.black;
                    zNode.parent.parent.color = RBTNode.red;
                    rightRotate(zNode.parent.parent);
                }
            } else {
                // Der Elternknoten ist der rechte Kindknoten des Großelternknotens
                RBTNode yNode = zNode.parent.parent.left;
                if (yNode.color == RBTNode.red) {
                    // Fall 1: Der Onkelknoten ist rot
                    zNode.parent.color = RBTNode.black;
                    yNode.color = RBTNode.black;
                    zNode.parent.parent.color = RBTNode.red;
                    zNode = zNode.parent.parent;
                } else {
                    // Der Onkelknoten ist schwarz
                    if (zNode == zNode.parent.left) {
                        // Fall 2: zNode ist linkes Kind
                        zNode = zNode.parent;
                        rightRotate(zNode); // Hier ist der Fehler - sollte rightRotate sein
                    }
                    // Fall 3: zNode ist rechtes Kind
                    zNode.parent.color = RBTNode.black;
                    zNode.parent.parent.color = RBTNode.red;
                    leftRotate(zNode.parent.parent);
                }
            }
        }
        root.color = RBTNode.black;
    }

    public boolean checkBlackHeight() {
        return getBlackHeight(root) != -1;
    }

    private int getBlackHeight(RBTNode node) {
        if (node == nil) {
            return 1; // NIL-Knoten zählt als schwarz
        }

        int leftBlackHeight = getBlackHeight(node.left);
        int rightBlackHeight = getBlackHeight(node.right);

        if (leftBlackHeight == -1 || rightBlackHeight == -1 || leftBlackHeight != rightBlackHeight) {
            return -1; // Ungültiger Pfad
        }

        int increment = (node.color == RBTNode.black) ? 1 : 0;
        return leftBlackHeight + increment;
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

