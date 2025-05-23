/**
 * The {@code Calculator} class provides methods to evaluate arithmetic expressions
 * represented as a {@link ParseTree}. It supports evaluation of expressions with binary
 * operators (+, -, *, /) and unary postfix operators (++ and --).
 * <p>
 * This class performs evaluation using a recursive traversal of the parse tree,
 * following a post-order approach to respect operator precedence and associativity.
 */
public class Calculator {

    /**
     * Recursively evaluates a {@link ParseTree} representing an arithmetic expression.
     * <p>
     * The method supports:
     * <ul>
     *   <li>Leaf nodes representing numeric literals</li>
     *   <li>Unary postfix operators: {@code ++}, {@code --}</li>
     *   <li>Binary operators: {@code +}, {@code -}, {@code *}, {@code /}</li>
     * </ul>
     *
     * @param node the root of the {@link ParseTree} to evaluate
     * @return the integer result of evaluating the expression
     * @throws NumberFormatException if a leaf node value is not a valid integer
     * @throws RuntimeException if an unknown operator is encountered
     */
    public static int evaluate(ParseTree node) {
        String val = node.value;

        // Leaf node (number)
        if (node.children.isEmpty()) {
            return Integer.parseInt(val);
        }

        // Unary operator (postfix)
        if (node.children.size() == 1) {
            int operand = evaluate(node.children.get(0));
            return switch (val) {
                case "++" -> operand + 1;
                case "--" -> operand - 1;
                default -> throw new RuntimeException("Unknown unary operator: " + val);
            };
        }

        // Binary operator
        int left = evaluate(node.children.get(0));
        int right = evaluate(node.children.get(1));

        return switch (val) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            case "/" -> left / right;
            default -> throw new RuntimeException("Unknown binary operator: " + val);
        };
    }

    /**
     * Evaluates the given {@link ParseTree} and prints the result to standard output.
     *
     * @param node the root of the {@link ParseTree} representing the expression
     */
    public void print(ParseTree node){

        int i = evaluate(node);

        System.out.println(i);
    }
}
