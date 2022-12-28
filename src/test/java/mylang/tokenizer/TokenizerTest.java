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
    public void testTokenizeIdentfier() {
        final Token[] tokens = Tokenizer.tokenize("bar");
        final Token[] expected = new Token[]{ new IdentifierToken("bar") };
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testTokenizeWholeVardec() {
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
}
