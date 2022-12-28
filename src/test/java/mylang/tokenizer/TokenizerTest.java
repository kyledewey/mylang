package mylang.tokenizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class TokenizerTest {
    @Test
    public void testIdentiferEquals() {
        assertEquals(new IdentifierToken("foo"),
                     new IdentifierToken("foo"));
    }

    @Test
    public void testTokenizeIdentfier() throws TokenizerException {
        final Token[] tokens = Tokenizer.tokenize("bar");
        final Token[] expected = new Token[]{ new IdentifierToken("bar") };
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testTokenizeLeftParen() throws TokenizerException {
        final Token[] tokens = Tokenizer.tokenize("(");
        final Token[] expected = new Token[]{ new LeftParenToken() };
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testTokenizeRightParen() throws TokenizerException {
        final Token[] tokens = Tokenizer.tokenize(")");
        final Token[] expected = new Token[]{ new RightParenToken() };
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testVardec() throws TokenizerException {
        final Token[] tokens = Tokenizer.tokenize("vardec");
        final Token[] expected = new Token[]{ new VardecToken() };
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testInt() throws TokenizerException {
        final Token[] tokens = Tokenizer.tokenize("int");
        final Token[] expected = new Token[]{ new IntToken() };
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testBool() throws TokenizerException {
        final Token[] tokens = Tokenizer.tokenize("bool");
        final Token[] expected = new Token[]{ new BoolToken() };
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testTrue() throws TokenizerException {
        final Token[] tokens = Tokenizer.tokenize("true");
        final Token[] expected = new Token[]{ new TrueToken() };
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testFalse() throws TokenizerException {
        final Token[] tokens = Tokenizer.tokenize("false");
        final Token[] expected = new Token[]{ new FalseToken() };
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testWhile() throws TokenizerException {
        final Token[] tokens = Tokenizer.tokenize("while");
        final Token[] expected = new Token[]{ new WhileToken() };
        assertArrayEquals(expected, tokens);
    }
    
    @Test
    public void testSingleCharacterIdentifier() throws TokenizerException {
        final Token[] tokens = Tokenizer.tokenize("x");
        final Token[] expected = new Token[]{ new IdentifierToken("x") };
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testSingleDigitNumber() throws TokenizerException {
        final Token[] tokens = Tokenizer.tokenize("7");
        final Token[] expected = new Token[]{ new NumberToken(7) };
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testMultiDigitNumber() throws TokenizerException {
        final Token[] tokens = Tokenizer.tokenize("1234567");
        final Token[] expected = new Token[]{ new NumberToken(1234567) };
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testIdAndNumber() throws TokenizerException {
        final Token[] tokens = Tokenizer.tokenize("7)");
        final Token[] expected = new Token[]{
            new NumberToken(7),
            new RightParenToken()
        };
        assertArrayEquals(expected, tokens);
    }
    
    @Test
    public void testTokenizeWholeVardec() throws TokenizerException {
        final Token[] tokens = Tokenizer.tokenize("(vardec int x 7)");
        final Token[] expected = new Token[]{
            new LeftParenToken(),
            new VardecToken(),
            new IntToken(),
            new IdentifierToken("x"),
            new NumberToken(7),
            new RightParenToken()
        };
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testTokenizeEmptyInput() throws TokenizerException {
        assertArrayEquals(new Token[0], Tokenizer.tokenize(""));
    }

    @Test
    public void testTokenizeOnlyWhitespace() throws TokenizerException {
        assertArrayEquals(new Token[0], Tokenizer.tokenize("   "));
    }

    @Test(expected = TokenizerException.class)
    public void testCannotTokenize() throws TokenizerException {
        Tokenizer.tokenize("$");
    }
}
