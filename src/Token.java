/**
 * Represents a lexical token with a specific type and associated string value.
 */
public class Token {

    /** The type of this token (e.g., PLUS, NUM, LPAR) */
    final TokenType type;

    /** The string value associated with the token */
    final String value;

    /**
     * Constructs a token with the given type and value.
     *
     * @param type  the type of token (e.g., NUM, PLUS)
     * @param value the string value of the token (e.g., "42", "+")
     */
    public Token(TokenType type, String value){
        this.type = type;
        this.value = value;
    }

    /**
     * Returns a string representation of the token for debugging.
     *
     * @return a string in the format "TYPE=value"
     */
    @Override
    public String toString() {
        return type.toString() + "=" + value;
    }
}
