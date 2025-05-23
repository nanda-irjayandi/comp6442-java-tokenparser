import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ParseTree {
    final String value;
    final List<ParseTree> children;

    ParseTree(String value, List<ParseTree> children){
        this.value = value;
        this.children = children;
    }

    /***
     * Overloaded constructor for the leaf node that creates an empty list
     * @param value
     */
    ParseTree(String value){
        this.value = value;
        this.children = new ArrayList<>();
    }

    ParseTree(String value, ParseTree... children){
        this.value = value;
        this.children = Arrays.asList(children);
    }

    public static void printTree(ParseTree node) {
        printTree(node, "", "");
    }

    private static void printTree(ParseTree node, String prefix, String childPrefix) {
        System.out.println(prefix + node.value);
        for (Iterator<ParseTree> it = node.children.iterator(); it.hasNext(); ) {
            ParseTree next = it.next();
            if (it.hasNext()) {
                printTree(next, childPrefix + "├── ", childPrefix + "│   ");
            } else {
                printTree(next, childPrefix + "└── ", childPrefix + "    ");
            }
        }
    }

    public static void main(String[] args) {
        ParseTree root = new ParseTree("+", Arrays.asList(
                new ParseTree("1"),
                new ParseTree("2"),
                new ParseTree("4")
        ));

        ParseTree.printTree(root);
    }
}
