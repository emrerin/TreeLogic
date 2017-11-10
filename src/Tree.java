//package ads1.ss15.pa;
import java.util.*;

public class Tree {

    public class Node {
        int top;
        int elem;
        Node left = null;
        Node right = null;
        ArrayList<Integer> list;

        Node(int elem) {
            this.elem = elem;
            this.top = 1;
        }

        void insert(int elem) {
            if (elem < this.elem) {
                this.top++;
                if (this.left == null) {
                    this.left = new Node(elem);
                }
                else {
                    this.left.insert(elem);
                }
            }
            else if (elem > this.elem) {
                this.top++;
                if (this.right == null) {
                    this.right = new Node(elem);
                }
                else {
                    this.right.insert(elem);
                }
            }
        }

        int size() {
            return top;
        }

        int heightTree(Node p) {
            if (p == null) {
                return -1;
            }
            if (p.left == null || p.right == null) {
                return 0;
            }

            return 1 + Math.max(heightTree(p.left), heightTree(p.right));
        }

        boolean nodeExists(Node p, int elem) {
            if (p.elem == elem) {
                return true;
            }
            else if (p.elem < elem) {
                if (p.right == null) {
                    return false;
                }
                else {
                    return nodeExists(p.right, elem);
                }
            }
            else if (p.elem > elem) {
                if (p.left == null) {
                    return false;
                }
                else {
                    return nodeExists(p.left, elem);
                }
            }
            return false;
        }

        int valueAt(int pos, int top) {
            if (pos == top) {
                return this.elem;
            }
            else if (pos < top) {
                if (this.left.right != null) {
                    top = top - this.left.right.size() - 1;
                }
                else {
                    top = top - 1;
                }
                if(this.left.right != null) {
                    return this.left.valueAt(pos, top);
                }
                else {
                    return this.left.elem;
                }
            }
            else {
                if (this.right.left != null) {
                    top = top + this.right.left.size() + 1;

                }
                else {
                    top = top + 1;
                }
                if(this.right.left != null){
                    return this.right.valueAt(pos, top);
                }
                else{
                    return this.right.elem;
                }
            }
        }
        int nodePos(int elem, int anzahl) {
            if (this.elem == elem) {
                return anzahl;
            } else if (elem < this.elem) {
                if (this.left == null) {
                    return anzahl;
                }
                if (this.left.right != null) {
                    anzahl = anzahl - this.left.right.size() - 1;
                } else {
                    anzahl = anzahl - 1;
                }
                if(this.left.right != null) {
                    return this.left.nodePos(elem, anzahl);
                }
                else
                    return anzahl;
            }
            else {
                if (this.right == null) {
                    return anzahl + 1;
                }
                if (this.right.left != null) {
                    anzahl = anzahl + this.right.left.size() + 1;
                }
                else {
                    anzahl = anzahl + 1;
                }
                if(this.right.left != null) {
                    return this.right.nodePos(elem, anzahl);
                }
                else
                    return anzahl;
            }
        }

        Node delete(Node x, int elem) {
            if (x == null) {
                return null;
            }

            int cmp = elem == x.elem ? 0 : elem > x.elem ? 1 : -1;

            if (cmp < 0) {
                x.left = delete(x.left, elem);
            }
            else if (cmp > 0) {
                x.right = delete(x.right, elem);
            }
            else {
                if (x.right == null) {
                    return x.left;
                }
                if (x.left == null) {
                    return x.right;
                }
                Node t = x;
                x = min(t.right);
                x.right = deleteMin(t.right);
                x.left = t.left;
            }
            x.top = x.top - 1;
            return x;
        }

        private Node deleteMin(Node x) {
            if (x.left == null){
                return x.right;
            }
            x.left = deleteMin(x.left);
            x.top = x.top - 1;
            return x;
        }

        private Node min(Node x) {
            if (x.left == null){
                return x;
            }
            else {
                return min(x.left);
            }
        }

        ArrayList<Integer> inOrder(Node p) {
            list = new ArrayList<Integer>();
            inOrder(p, list);
            return list;
        }

        void inOrder(Node p, ArrayList<Integer> arr) {
            if (p != null) {
                if(p.left != null){
                    inOrder(p.left, arr);
                }

                arr.add(p.elem);

                if(p.left != null){
                    inOrder(p.right, arr);
                }
            }
        }
    }

    private Node root = null;

    public boolean isEmpty() {
        return this.root == null;
    }

    public int size() {
        if (isEmpty()) {
            return 0;
        }
        else {
            return this.root.size();
        }
    }

    public boolean exists(int val) {
        return root.nodeExists(root, val);
    }

    public int height() {
        if(root == null){
            return -1;
        }
        else {
            return root.heightTree(root);
        }
    }

    public void insert(int val) {
        if (isEmpty()) {
            this.root = new Node(val);
        }
        else {
            if (!exists(val)) {
                this.root.insert(val);
            }
        }
    }

    public void delete(int val) {
        root.delete(root, val);
    }

    public int valueAtPosition(int k) {
        if (k < 0 || k > size() - 1) {
            throw new IllegalArgumentException();
        }
        else {
            return root.valueAt(k, root.left.top);
        }
    }

    public int position(int val) {
        return root.nodePos(val, root.left.top);
    }

    public Iterable<Integer> values(int lo, int hi) {
        ArrayList<Integer> arr = root.inOrder(root);
        Queue<Integer> list = new LinkedList<Integer>();
        Iterator<Integer> t = arr.iterator();
        if (lo > hi) {
            while (t.hasNext()) {
                int x = t.next();
                if (x >= lo || x <= hi) {
                    list.add(x);
                }
            }
        }
        else {
            while (t.hasNext()) {
                int x = t.next();
                if (x >= lo && x <= hi) {
                    list.add(x);
                }
            }
        }
        return list;
    }

    public void simpleBalance() {
        int start = 0;
        int end = size() - 1;
        Tree t = new Tree();
        mergeTree(t, start, end);
        root = t.root;
    }

    void mergeTree(Tree neu, int lo, int hi) {
        int mid = 0;
        if (lo <= hi) {
            mid = ((lo + hi) / 2);
            neu.insert(valueAtPosition(mid));
            mergeTree(neu, lo, mid - 1);
            mergeTree(neu, mid + 1, hi);
        }
    }

    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.insert(30);
        tree.insert(20);
        tree.insert(45);
        tree.insert(38);
        tree.insert(50);
        tree.insert(10);
        tree.insert(25);
        tree.insert(22);
        tree.insert(29);
        tree.insert(5);
        tree.insert(13);
        tree.insert(12);
        tree.insert(14);
        System.out.println("isEmpty(): " + tree.isEmpty());
        System.out.println("size(): " + tree.size());
        System.out.println("height(): " + tree.height());
        System.out.println("exists(20): " + tree.exists(20));
        System.out.println("exists(23): " + tree.exists(23));
        System.out.println("valueAtPosition(3): " + tree.valueAtPosition(3));
        System.out.println("valueAtPosition(4): " + tree.valueAtPosition(4));
        System.out.println("valueAtPosition(0): " + tree.valueAtPosition(0));
        System.out.println("position(10): " + tree.position(10));
        System.out.println("position(29): " + tree.position(29));
        System.out.println("position(31): " + tree.position(31));
        System.out.println("values (14,38):" + tree.values(14, 38));
        System.out.println("values (38,14):" + tree.values(38, 14));
        // tree.insert(11);
        // tree.delete(10);
        System.out.println("valueAtPosition(1): " + tree.valueAtPosition(1));
        //  tree.simpleBalance();
        System.out.println(tree.position(5));
        System.out.println(tree.position(10));
        System.out.println(tree.position(12));
        System.out.println(tree.position(13));
        System.out.println(tree.position(14));
        System.out.println(tree.position(20));
        System.out.println(tree.position(22));
        System.out.println(tree.position(25));
        System.out.println(tree.position(29));
        System.out.println(tree.position(30));
        System.out.println(tree.position(38));
        System.out.println(tree.position(45));
        System.out.println(tree.position(50));
        System.out.println(tree.position(4));     // 0
        System.out.println(tree.position(6)); //     1
        System.out.println(tree.position(11));    // 2
        System.out.println(tree.position(15)); //    5
        System.out.println(tree.position(21));   //  6
        System.out.println(tree.position(24));  //   7
        System.out.println(tree.position(26));   //  8
        System.out.println(tree.position(30));   //  9
        System.out.println(tree.position(34));   //  10
        System.out.println(tree.position(40));  //   11
        System.out.println(tree.position(47));   //  12
        System.out.println(tree.position(55)); //    13
    }
}