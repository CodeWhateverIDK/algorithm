package com.lxl;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author llllxl
 * @since 2025/07/08 16:40
 */
public class SplayTree {
    public static void main(String[] args) {
        SplayTree tree = new SplayTree();

        tree.insert(100);
        tree.insert(50);
        tree.insert(75);

        System.out.println("Before splay(75):");
        tree.printTree();

        tree.splay(tree.search(tree.root, 75));

        System.out.println("After splay(75):");
        tree.printTree();
    }
    // In SplayTree.java
    public void printTree() {
        printTree(root, 0);
    }

    private void printTree(Node node, int depth) {
        if (node == null) return;
        printTree(node.r, depth + 1);
        for (int i = 0; i < depth; i++) System.out.print("    ");
        System.out.println(node.v + (node == root ? " (root)" : ""));
        printTree(node.l, depth + 1);
    }

    private Node root;

    class Node {
        int v;
        Node l, r, p;

        Node(int v) {
            this.v = v;
            l = r = p = null;
        }
    }

    public void zig(Node x) {
        Node p = x.p;
        p.l = x.r;
        if (x.r != null) {
            x.r.p = p;
        }
        x.p = p.p;
        if (p.p != null) {
            if (p.p.r == p) {
                p.p.r = x;
            } else {
                p.p.l = x;
            }
        }
        p.p = x;
        x.r = p;
    }

    public void zag(Node x) {
        Node p = x.p;
        p.r = x.l;
        if (x.l != null) {
            x.l.p = p;
        }
        x.p = p.p;
        if (p.p != null) {
            if (p.p.l == p) {
                p.p.l = x;
            } else {
                p.p.r = x;
            }
        }
        p.p = x;
        x.l = p;
    }

    public void splay(Node x) {
        while (x.p != null) {
            Node p = x.p;
            if (p.l == x) {
                if (p.p != null && p.p.l == p) {
                    zig(p);
                }
                zig(x);
            } else {
                if (p.p != null && p.p.r == p) {
                    zag(p);
                }
                zag(x);
            }
        }
        root = x;
    }

    public Node search(Node root, int v) {
        while (root != null && root.v != v) {
            if (v < root.v) {
                root = root.l;
            } else {
                root = root.r;
            }
        }
        return root;
    }

    public void insert(int v) {
        if (root == null) {
            root = new Node(v);
            return;
        }
        Node newNode = new Node(v);
        Node c = root;
        Node p = root.p;
        while (c != null) {
            p = c;
            if (v < c.v) {
                c = c.l;
            } else if (v > c.v) {
                c = c.r;
            } else {
                return;
            }
        }
        newNode.p = p;
        if (v < p.v) {
            p.l = newNode;
        } else {
            p.r = newNode;
        }
    }

    public Node pre(int v) {
        splay(search(root, v));
        Node pre = root.l;
        if (pre == null) {
            return null;
        }
        while (pre.r != null) {
            pre = pre.r;
        }
        return pre;
    }

    public void remove(int v) {
        Node x = search(root, v);
        Node pre = pre(v);
        if (pre == null) {
            splay(x);
            root = x.r;
            if (root != null) {
                root.p = null;
            }
        } else {
            splay(pre);
            root = pre;
            if (x.r != null) {
                x.r.p = pre;
            }
            pre.r = x.r;
        }
    }
}
