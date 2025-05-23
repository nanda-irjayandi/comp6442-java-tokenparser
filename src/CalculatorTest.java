import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the Calculator class.
 */
public class CalculatorTest {

    /**
     * Convenience wrapper to print tree and evaluated result.
     */
    private void printResult(ParseTree tree) {
        System.out.println("Parse Tree:");
        ParseTree.printTree(tree);
        System.out.println("Evaluation Result: " + Calculator.evaluate(tree));
    }

    /**
     * Builds a ParseTree node with given value and children.
     */
    private ParseTree node(String value, ParseTree... children) {
        List<ParseTree> list = new ArrayList<>();
        for (ParseTree c : children) list.add(c);
        return new ParseTree(value, list);
    }

    /**
     * Leaf node with number.
     */
    private ParseTree num(String value) {
        return new ParseTree(value, new ArrayList<>());
    }

    @Test
    public void testSimpleAddition() {
        ParseTree tree = node("+", num("2"), num("3"));
        printResult(tree);
        assertEquals(5, Calculator.evaluate(tree));
    }

    @Test
    public void testNestedExpression() {
        // 2 + (3 * 4)
        ParseTree tree = node("+",
                num("2"),
                node("*", num("3"), num("4"))
        );
        printResult(tree);
        assertEquals(14, Calculator.evaluate(tree));
    }

    @Test
    public void testUnaryPostfix() {
        // (7)++
        ParseTree tree = node("++", num("7"));
        printResult(tree);
        assertEquals(8, Calculator.evaluate(tree));
    }

    @Test
    public void testComplexMixedExpression() {
        // Construct a large mixed expression:
        // (((1 + 2) * (3 + 4)) + ((5 + 6) * (7 + 8)) + ((9 + 10) * (11 + 12)))
        // (1 + 2) * (3 + 4) = 3 * 7 = 21
        // (5 + 6) * (7 + 8) = 11 * 15 = 165
        // (9 + 10) * (11 + 12) = 19 * 23 = 437
        // 21 + 165 + 437 = 623

        ParseTree tree = node("+",
                node("+",
                        node("*",
                                node("+", num("1"), num("2")),
                                node("+", num("3"), num("4"))
                        ),
                        node("*",
                                node("+", num("5"), num("6")),
                                node("+", num("7"), num("8"))
                        )
                ),
                node("*",
                        node("+", num("9"), num("10")),
                        node("+", num("11"), num("12"))
                )
        );
        printResult(tree);
        assertEquals(623, Calculator.evaluate(tree));
    }
}
