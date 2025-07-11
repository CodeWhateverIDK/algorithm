package com.lxl;

/**
 * @author llllxl
 * @since 2025/07/11 10:45
 */
public class SplayTreeTest {

    public static void main(String[] args) {
        SplayTree tree = new SplayTree();
        SplayTree.Node root = null;

        // 插入多个节点
        int[] values = {100, 50, 200, 40, 30, 60, 150, 300};
        for (int v : values) {
            root = tree.insert(root, v);
        }

        System.out.println("=== 中序遍历（插入完成后） ===");
        inorder(root);
        System.out.println();

        // 测试查找是否将节点伸展到根
        int searchVal = 60;
        root = tree.splay(root, searchVal);
        System.out.println("\n=== 查找 " + searchVal + " 并伸展到根 ===");
        System.out.println("根节点是：" + root.v);
        System.out.println("中序遍历：");
        inorder(root);

        // 测试插入重复节点
        System.out.println("\n=== 插入重复节点 60 ===");
        root = tree.insert(root, 60);
        System.out.println("根节点还是：" + root.v + " (应该还是60)");
        System.out.println("中序遍历：");
        inorder(root);

        System.out.println("\n=== 删除节点 60 ===");
        root = tree.remove(root, 60);
        System.out.println("中序遍历（删除60后）:");
        inorder(root);
        System.out.println("\n层级结构:");
        printTree(root, "", true);

        System.out.println("\n=== 删除最小节点 30 ===");
        root = tree.remove(root, 30);
        System.out.println("中序遍历（删除30后）:");
        inorder(root);
        System.out.println("\n层级结构:");
        printTree(root, "", true);

        System.out.println("\n=== 删除最大节点 300 ===");
        root = tree.remove(root, 300);
        System.out.println("中序遍历（删除300后）:");
        inorder(root);
        System.out.println("\n层级结构:");
        printTree(root, "", true);

        System.out.println("\n=== 删除不存在的节点 999 ===");
        root = tree.remove(root, 999);
        System.out.println("中序遍历（尝试删除999后）:");
        inorder(root);
        System.out.println("\n层级结构:");
        printTree(root, "", true);

        // 打印结构化树
        System.out.println("\n=== 层级结构打印树 ===");
        printTree(root, "", true);
    }

    public static void inorder(SplayTree.Node root) {
        if (root != null) {
            inorder(root.l);
            System.out.print(root.v + " ");
            inorder(root.r);
        }
    }

    public static void printTree(SplayTree.Node node, String prefix, boolean isTail) {
        if (node == null) return;
        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.v);
        if (node.l != null || node.r != null) {
            printTree(node.r, prefix + (isTail ? "    " : "│   "), false);
            printTree(node.l, prefix + (isTail ? "    " : "│   "), true);
        }
    }
}
