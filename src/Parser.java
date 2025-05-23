import java.util.List;

/**
 * A recursive descent parser for arithmetic expressions involving binary and unary (postfix) operators.
 * <p>
 * The grammar supported is:
 * <pre>
 * expression  ::= term (("+" | "-") term)* ;
 * term        ::= factor (("*" | "/") factor)* ;
 * factor      ::= primary postfix_op? ;
 * primary     ::= NUMBER | "(" expression ")" ;
 * postfix_op  ::= "++" | "--" ;
 * </pre>
 * </p>
 * The resulting abstract syntax tree is represented using {@link ParseTree}.
 */
public class Parser {
    private final List<Token> tokens;
    private int pos = 0;

    /**
     * Constructs a parser with the given token stream.
     *
     * @param tokens the list of tokens to parse
     */
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Parses the token stream and returns the root of the expression parse tree.
     *
     * @return the root of the parse tree
     */
    public ParseTree parse() {
        return parseExpression();
    }

    /**
     * Parses an expression consisting of one or more terms separated by '+' or '-'.
     *
     * @return the root of the expression subtree
     */
    private ParseTree parseExpression() {
        ParseTree left = parseTerm();
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Token op = previous();
            ParseTree right = parseTerm();
            left = new ParseTree(op.value, left, right);
        }
        return left;
    }

    /**
     * Parses a term consisting of one or more factors separated by '*' or '/'.
     *
     * @return the root of the term subtree
     */
    private ParseTree parseTerm() {
        ParseTree left = parseFactor();
        while (match(TokenType.MULT, TokenType.DIV)) {
            Token op = previous();
            ParseTree right = parseFactor();
            left = new ParseTree(op.value, left, right);
        }
        return left;
    }

    /**
     * Parses a factor which may consist of a primary followed by an optional postfix operator.
     *
     * @return the root of the factor subtree
     */
    private ParseTree parseFactor() {
        ParseTree node = parsePrimary();

        while (match(TokenType.INC, TokenType.DEC)) {
            Token op = previous();
            node = new ParseTree(op.value, node);
        }

        return node;
    }

    /**
     * Parses a primary expression, which may be a number or a parenthesized expression.
     *
     * @return the root of the primary subtree
     */
    private ParseTree parsePrimary() {
        if (match(TokenType.NUM)) {
            return new ParseTree(previous().value);
        }

        if (match(TokenType.LPAR)) {
            ParseTree expr = parseExpression();
            if (!match(TokenType.RPAR)) {
                throw new RuntimeException("Expected ')'");
            }
            return expr;
        }

        throw new RuntimeException("Unexpected token: " + peek());
    }

    /**
     * Checks if the current token matches any of the given types and advances the position if matched.
     *
     * @param types the token types to match
     * @return true if a match was found; false otherwise
     */
    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                pos++;
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the current token is of the specified type.
     *
     * @param type the token type to check
     * @return true if current token matches the type; false otherwise
     */
    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return tokens.get(pos).type == type;
    }

    /**
     * Returns the previously consumed token.
     *
     * @return the previous token
     */
    private Token previous() {
        return tokens.get(pos - 1);
    }

    /**
     * Returns the current token without consuming it.
     *
     * @return the current token
     */
    private Token peek() {
        return tokens.get(pos);
    }

    /**
     * Checks if the parser has reached the end of the token stream.
     *
     * @return true if at the end; false otherwise
     */
    private boolean isAtEnd() {
        return pos >= tokens.size() || tokens.get(pos).type == TokenType.EOF;
    }
}
