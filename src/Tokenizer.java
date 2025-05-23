import java.util.ArrayList;
import java.util.List;

/**
 * A simple tokenizer that converts an arithmetic expression string into a list of tokens.
 * Supports numbers, binary operators, parentheses, and postfix increment/decrement.
 */
public class Tokenizer {
    private final String input;
    private int pos = 0;

    /**
     * Constructs a tokenizer for the given input string.
     * Whitespace characters are stripped out automatically.
     *
     * @param input the raw input expression to tokenize
     */
    public Tokenizer(String input){
        this.input = input.replaceAll("\\s+", "");
    }

    /**
     * Peeks at the next character without advancing the position.
     *
     * @return the next character, or '\0' if at the end of the input
     */
    private char peek(){
        return (pos + 1 < input.length()) ? input.charAt(pos + 1) : '\0';
    }

    /**
     * Tokenizes the input expression into a list of {@link Token} objects.
     *
     * @return a list of tokens ending with an EOF token
     * @throws IllegalArgumentException if an unrecognized character is encountered
     */
    public List<Token> tokenize(){
        List<Token> tokens = new ArrayList<>();

        while (pos < input.length()) {
            char c = input.charAt(pos);

            // Numeric literal
            if (Character.isDigit(c)) {
                int start = pos;
                while (pos < input.length() && Character.isDigit(input.charAt(pos))) pos++;
                String number = input.substring(start, pos);
                tokens.add(new Token(TokenType.NUM, number));
                continue;
            }

            // Operators and parentheses
            switch (c) {
                case '*':
                    tokens.add(new Token(TokenType.MULT, "*"));
                    pos++;
                    break;
                case '/':
                    tokens.add(new Token(TokenType.DIV, "/"));
                    pos++;
                    break;
                case '(':
                    tokens.add(new Token(TokenType.LPAR, "("));
                    pos++;
                    break;
                case ')':
                    tokens.add(new Token(TokenType.RPAR, ")"));
                    pos++;
                    break;
                case '+':
                    if (peek() == '+') {
                        tokens.add(new Token(TokenType.INC, "++"));
                        pos += 2;
                    } else {
                        tokens.add(new Token(TokenType.PLUS, "+"));
                        pos += 1;
                    }
                    break;
                case '-':
                    if (peek() == '-') {
                        tokens.add(new Token(TokenType.DEC, "--"));
                        pos += 2;
                    } else {
                        tokens.add(new Token(TokenType.MINUS, "-"));
                        pos += 1;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized character: '" + c + "'");
            }
        }

        // Add EOF token at the end
        tokens.add(new Token(TokenType.EOF, "EOF"));
        return tokens;
    }
}
