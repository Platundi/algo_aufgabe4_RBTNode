/* Quellen:
 * https://www.geeksforgeeks.org/binary-search-tree-set-1-search-and-insertion/
 * https://www.geeksforgeeks.org/binary-search-tree-set-3-iterative-delete/
 * https://www.geeksforgeeks.org/insertion-in-binary-search-tree/ */

public class RedBlackTree {
    private RBTNode nil = new RBTNode(-1, "nil");
    private RBTNode root;

    public RedBlackTree() {
        this.root = null;
    } // Konstruktor

    public void insert(int key, String val) {
        root = insert(root, key, val);
    }

    private RBTNode insert(RBTNode node, int key, String val) {
        if (node == null)
            return new RBTNode(key, val);

        if (key < node.key)
            node.left = insert(node.left, key, val);
        else if (key > node.key)
            node.right = insert(node.right, key, val);
        return node;
    }

    public String search(int key) {
        return search(root, key);
    }

    private String search(RBTNode node, int key) {
        if (node == null || node.key == key) {
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
        if (node == null)
            return -1;
        int lHeight = height(node.left);
        int rHeight = height(node.right);
        return Math.max(lHeight, rHeight) + 1;
    }


    // this function performs left rotation
    Node rotateLeft(Node node)
    {
        Node x = node.right;
        Node y = x.left;
        x.left = node;
        node.right = y;
        node.parent = x; // parent resetting is also important.
        if(y!=null)
            y.parent = node;
        return(x);
    }
    //this function performs right rotation
    Node rotateRight(Node node)
    {
        Node x = node.left;
        Node y = x.right;
        x.right = node;
        node.left = y;
        node.parent = x;
        if(y!=null)
            y.parent = node;
        return(x);
    }


    public boolean CheckRB() { ... )
    }

