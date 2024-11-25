// AVL Tree Node class
class AVLNode {
    int key;           // Key for the region, can be ID or another identifier
    String data;       // Data describing the affected region
    int height;        // Height of the node in the tree
    AVLNode left, right; // Left and right children

    // Constructor to initialize the node with a key and data
    public AVLNode(int key, String data) {
        this.key = key;
        this.data = data;
        this.height = 1;  // Initially, the height is 1 when the node is added
    }
}

// AVL Tree class
class AVLTree {
    private AVLNode root;  // Root of the tree

    // Get the height of a node
    private int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    // Get the balance factor of a node
    private int getBalance(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    // Right rotate the subtree rooted at the given node
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Return the new root
        return x;
    }

    // Left rotate the subtree rooted at the given node
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Return the new root
        return y;
    }

    // Insert a new node with the specified key and data
    public void insert(int key, String data) {
        root = insertNode(root, key, data);
    }

    // Recursive function to insert a node in the AVL tree
    private AVLNode insertNode(AVLNode node, int key, String data) {
        // Perform normal BST insertion
        if (node == null) {
            return new AVLNode(key, data);
        }

        if (key < node.key) {
            node.left = insertNode(node.left, key, data);
        } else if (key > node.key) {
            node.right = insertNode(node.right, key, data);
        } else {
            // Duplicate keys are not allowed in the AVL tree
            return node;
        }

        // Update the height of the current node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Check the balance factor and balance the tree
        int balance = getBalance(node);

        // Left Left case
        if (balance > 1 && key < node.left.key) {
            return rightRotate(node);
        }

        // Right Right case
        if (balance < -1 && key > node.right.key) {
            return leftRotate(node);
        }

        // Left Right case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // Return the unchanged node
        return node;
    }

    // Delete a node from the AVL tree
    public void delete(int key) {
        root = deleteNode(root, key);
    }

    // Recursive function to delete a node
    private AVLNode deleteNode(AVLNode root, int key) {
        // Perform standard BST deletion
        if (root == null) {
            return root;
        }

        if (key < root.key) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.key) {
            root.right = deleteNode(root.right, key);
        } else {
            // Node with only one child or no child
            if (root.left == null || root.right == null) {
                AVLNode temp = (root.left != null) ? root.left : root.right;
                if (temp == null) {
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                // Node with two children: Get the in-order successor
                AVLNode temp = minValueNode(root.right);
                root.key = temp.key;
                root.data = temp.data;
                root.right = deleteNode(root.right, temp.key);
            }
        }

        // If the tree has only one node, return
        if (root == null) {
            return root;
        }

        // Update the height of the current node
        root.height = Math.max(height(root.left), height(root.right)) + 1;

        // Get the balance factor of the node
        int balance = getBalance(root);

        // Balance the tree if it is unbalanced

        // Left Left case
        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }

        // Right Right case
        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }

        // Left Right case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Left case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // Utility function to find the node with the minimum key
    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // In-order traversal (Left, Root, Right)
    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    private void inOrderTraversal(AVLNode node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println("Key: " + node.key + ", Data: " + node.data);
            inOrderTraversal(node.right);
        }
    }

    // Pre-order traversal (Root, Left, Right)
    public void preOrderTraversal() {
        preOrderTraversal(root);
    }

    private void preOrderTraversal(AVLNode node) {
        if (node != null) {
            System.out.println("Key: " + node.key + ", Data: " + node.data);
            preOrderTraversal(node.left);
            preOrderTraversal(node.right);
        }
    }

    // Post-order traversal (Left, Right, Root)
    public void postOrderTraversal() {
        postOrderTraversal(root);
    }

    private void postOrderTraversal(AVLNode node) {
        if (node != null) {
            postOrderTraversal(node.left);
            postOrderTraversal(node.right);
            System.out.println("Key: " + node.key + ", Data: " + node.data);
        }
    }
}

// Main class to test the AVL Tree
public class Main {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        // Insert regions with IDs and descriptions
        tree.insert(10, "Region A");
        tree.insert(20, "Region B");
        tree.insert(5, "Region C");
        tree.insert(15, "Region D");
        tree.insert(30, "Region E");

        // Perform in-order, pre-order, and post-order traversals
        System.out.println("In-order Traversal:");
        tree.inOrderTraversal();

        System.out.println("\nPre-order Traversal:");
        tree.preOrderTraversal();

        System.out.println("\nPost-order Traversal:");
        tree.postOrderTraversal();

        // Delete a region
        tree.delete(20);

        // Perform in-order traversal after deletion
        System.out.println("\nIn-order Traversal After Deletion:");
        tree.inOrderTraversal();
    }
}
