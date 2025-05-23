public class Token {

    // +, -, *, /, (, ), ++, --
    public enum TokenType {
        INT,
        PLUS, MINUS, MULT, DIV,
        LPAR, RPAR,
        INC, DEC,
        EOF
    }

    final TokenType type;
    final String value;

    Token(TokenType type, String value){
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return type.toString() + "=" + value;
    }
}
