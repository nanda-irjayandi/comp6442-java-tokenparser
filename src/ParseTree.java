import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a node in a parse tree, which may contain a value and child nodes.
 * This class is commonly used to represent the abstract syntax tree (AST) of parsed expressions.
 */
public class ParseTree {
    /** The value of the node, typically an operator or a number */
    final String value;

    /** The child nodes of this parse tree node */
    final List<ParseTree> children;

    /**
     * Constructs a ParseTree node with a specified value and list of children.
     *
     * @param value    the value for the node (e.g., "+", "42")
     * @param children the list of child nodes
     */
    public ParseTree(String value, List<ParseTree> children) {
        this.value = value;
        this.children = children;
    }

    /**
     * Constructs a leaf node (no children) with the specified value.
     *
     * @param value the value of the leaf node
     */
    public ParseTree(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    /**
     * Constructs a node with the specified value and any number of children.
     *
     * @param value    the value of the node
     * @param children a varargs list of child nodes
     */
    public ParseTree(String value, ParseTree... children) {
        this.value = value;
        this.children = Arrays.asList(children);
    }

    /**
     * Pretty-prints the parse tree to the standard output using tree-like ASCII formatting.
     *
     * @param node the root of the tree to print
     */
    public static void printTree(ParseTree node) {
        printTree(node, "", "");
    }

    /**
     * Recursive helper method to print the tree in a structured format.
     *
     * @param node        the current node to print
     * @param prefix      the prefix for the current node
     * @param childPrefix the prefix to use for the node's children
     */
    private static void printTree(ParseTree node, String prefix, String childPrefix) {
        System.out.println(prefix + node.value);

        List<ParseTree> children = node.children;
        for (int i = children.size() - 1; i >= 0; i--) {
            ParseTree child = children.get(i);
            boolean isLast = (i == 0);
            String newPrefix = childPrefix + (isLast ? "└── " : "├── ");
            String newChildPrefix = childPrefix + (isLast ? "    " : "│   ");
            printTree(child, newPrefix, newChildPrefix);
        }
    }

    /**
     * Example usage and demonstration of the parse tree.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        ParseTree root = new ParseTree("+", Arrays.asList(
                new ParseTree("1"),
                new ParseTree("2"),
                new ParseTree("4")
        ));

        ParseTree.printTree(root);
    }
}
