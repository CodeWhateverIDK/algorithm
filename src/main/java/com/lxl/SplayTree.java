package com.lxl;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author llllxl
 * @since 2025/07/08 16:40
 */
public class SplayTree {



    class Node {
        int v;
        Node l, r, p;

        Node(int v) {
            this.v = v;
            l = r = p = null;
        }
    }

    public Node zig(Node root) {
        Node l = root.l;
        root.l = l.r;
        if (root.l != null) {
            root.l.p = root;
        }
        l.p = root.p;
        root.p = l;
        l.r = root;
        return l;
    }

    public Node zag(Node root) {
        Node r = root.r;
        root.r = r.l;
        if (root.r != null) {
            root.r.p = root;
        }
        r.p = root.p;
        root.p = r;
        r.l = root;
        return r;
    }

    public Node splay(Node root, int v) {
        if (root == null || root.v == v) {
            return root;
        }

        if (root.v > v) {
            if (root.l == null) {
                return root;
            }
            if (root.l.v > v && root.l.l != null) {
                root.l.l = splay(root.l.l, v);
                root = zig(root);
            } else if (root.l.v < v && root.l.r != null) {
                root.l.r = splay(root.l.r, v);
                if (root.l.r != null) {
                    root.l = zag(root.l);
                }
            }
            return root.l == null ? root : zig(root);
        } else {
            if (root.r == null) {
                return root;
            }
            if (root.r.v < v && root.r.r != null) {
                root.r.r = splay(root.r.r, v);
                root = zag(root);
            } else if (root.r.v > v && root.r.l != null) {
                root.r.l = splay(root.r.l, v);
                if (root.r.l != null) {
                    root.r = zig(root.r);
                }
            }
            return root.r == null ? root : zag(root);
        }
    }

    public Node insert(Node root, int v) {
        Node x = new Node(v);
        if (root == null) {
            return x;
        }
        Node splay = splay(root, v);
        if (splay.v == v) {
            return splay;
        } else if (splay.v > v) {
            x.r = splay;
            splay.p = x;
            x.l = splay.l;
            if (x.l != null) {
                x.l.p = x;
            }
            splay.l = null;
        } else {
            x.l = splay;
            splay.p = x;
            x.r = splay.r;
            if (x.r != null) {
                x.r.p = x;
            }
            splay.r = null;
        }
        return x;
    }

    public Node pre(Node root, int v) {
        if (root == null || root.l == null) {
            return root;
        }
        root = root.l;
        while (root.r != null) {
            root = root.r;
        }
        return root;
    }

    public Node remove(Node root, int v) {
        if (root == null) {
            return root;
        }
        root = splay(root, v);
        if (root.v != v) {
            return root;
        } else if (root.l == null) {
            if (root.r != null) {
                root.r.p = null;
            }
            return root.r;
        }
        Node pre = splay(root, pre(root, v).v);
        pre.r = root.r;
        if (root.r != null) {
            root.r.p = pre;
        }
        return pre;
    }
}
