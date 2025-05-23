import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final String input;
    private int pos = 0;

    Tokenizer(String input){
        this.input = input.replaceAll("\\s+", "");
    }

    private char peek(){
        return (pos + 1 < input.length()) ? input.charAt(pos + 1) : '\0'; // return null char if at the end of the line
    }

    public List<Token> tokenize(){
        List<Token> tokens = new ArrayList<>();

        while (pos < input.length()) {
            char c = input.charAt(pos);

            if (Character.isDigit(c)) {
                int start = pos;
                while (pos < input.length() && Character.isDigit(input.charAt(pos))) pos++;

                String number = input.substring(start, pos);

                tokens.add(new Token(TokenType.NUM, number));

                continue;
            }

            //        INT,
            //        PLUS, MINUS, MULT, DIV,
            //        LPAR, RPAR,
            //        INC, DEC,
            //        EOF

            switch (c) {
                case '*': tokens.add(new Token(TokenType.MULT, "*")); pos++; break;
                case '/': tokens.add(new Token(TokenType.DIV, "/")); pos++; break;
                case '(': tokens.add(new Token(TokenType.LPAR, "(")); pos++; break;
                case ')': tokens.add(new Token(TokenType.RPAR, ")")); pos++; break;
                case '+':
                    if (peek() == '+') {
                        tokens.add(new Token(TokenType.INC, "++"));
                        pos += 2;
                    }
                    else {
                        tokens.add(new Token(TokenType.PLUS, "+"));
                        pos += 1; // equivalent to pos++
                    }
                    break;
                case '-':
                    if (peek() == '-') {
                        tokens.add(new Token(TokenType.DEC, "--"));
                        pos += 2;
                    }
                    else {
                        tokens.add(new Token(TokenType.MINUS, "-"));
                        pos += 1;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized character");
            }
        }

        tokens.add(new Token(TokenType.EOF, "EOF")); // we can use "\0" but "EOF" is more meaningful for debugging
        return tokens;
    }
}
