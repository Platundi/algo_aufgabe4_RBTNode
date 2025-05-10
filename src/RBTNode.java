/* Quellen:
 * https://www.geeksforgeeks.org/binary-search-tree-set-1-search-and-insertion/
 * https://www.geeksforgeeks.org/insertion-in-red-black-tree/ */

public class RBTNode {
    public static final boolean black=false;
    public static final boolean red=true;
    public int key;
    public String val;
    public Boolean color;
    public RBTNode left, right, parent;

    public RBTNode(int k, String s) {
        this.key = k;
        this.val = s;
        this.color = red;
    }
}
