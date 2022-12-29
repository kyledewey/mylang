package mylang.tokenizer;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Tokenizer {
    public static final Map<String, Token> RESERVED_WORDS = new HashMap<String, Token>() {{
            put("int", new IntToken());
            put("bool", new BoolToken());
            put("vardec", new VardecToken());
            put("true", new TrueToken());
            put("false", new FalseToken());
            put("while", new WhileToken());
            put("print", new PrintToken());
            put("progn", new PrognToken());
        }};

    public static final SymbolPair[] SYMBOLS = new SymbolPair[] {
        new SymbolPair("(", new LeftParenToken()),
        new SymbolPair(")", new RightParenToken()),
        new SymbolPair("=", new SingleEqualsToken()),
        new SymbolPair("+", new PlusToken()),
        new SymbolPair("-", new MinusToken()),
        new SymbolPair("&&", new LogicalAndToken()),
        new SymbolPair("||", new LogicalOrToken()),
        new SymbolPair("<", new LessThanToken())
    };
    
    private final String input;
    private int position;

    public Tokenizer(final String input) {
        this.input = input;
        position = 0;
    }

    public void skipWhitespace() {
        while (position < input.length() &&
               Character.isWhitespace(input.charAt(position))) {
            position++;
        }
    }
    
    // returns null if it's not a symbol
    public Token tokenizeSymbol() {
        for (final SymbolPair pair : SYMBOLS) {
            if (input.startsWith(pair.asString, position)) {
                position += pair.asString.length();
                return pair.asToken;
            }
        }

        return null;
    }
    
    // returns null if it's not an identifier or reserved word
    public Token tokenizeIdentifierOrReservedWord() {
        String name = "";

        if (Character.isLetter(input.charAt(position))) {
            name += input.charAt(position);
            position++;
            while (position < input.length() &&
                   Character.isLetterOrDigit(input.charAt(position))) {
                name += input.charAt(position);
                position++;
            }

            if (RESERVED_WORDS.containsKey(name)) {
                return RESERVED_WORDS.get(name);
            } else {
                return new IdentifierToken(name);
            }
        } else {
            return null;
        }
    }
    
    // returns null if it's not a number
    public NumberToken tokenizeNumber() {
        String digits = "";
        while (position < input.length() &&
               Character.isDigit(input.charAt(position))) {
            digits += input.charAt(position);
            position++;
        }

        if (digits.length() > 0) {
            return new NumberToken(Integer.parseInt(digits));
        } else {
            return null;
        }
    }

    // returns null if there was no token
    // assumes we start on a non-whitespace character
    public Token readToken() throws TokenizerException {
        Token retval = tokenizeNumber();
        if (retval != null) {
            return retval;
        }
        retval = tokenizeIdentifierOrReservedWord();
        if (retval != null) {
            return retval;
        }
        retval = tokenizeSymbol();
        if (retval != null) {
            return retval;
        }
        throw new TokenizerException("Could not tokenize: " +
                                     input.charAt(position));
    }
    
    public Token[] tokenize() throws TokenizerException {
        final List<Token> retval = new ArrayList<Token>();
        skipWhitespace();
        while (position < input.length()) {
            retval.add(readToken());
            skipWhitespace();
        }
        return retval.toArray(new Token[retval.size()]);
    }

    public static Token[] tokenize(final String input)
        throws TokenizerException {
        return new Tokenizer(input).tokenize();
    }
}
