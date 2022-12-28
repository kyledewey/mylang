package mylang.parser;

import mylang.tokenizer.*;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ParserTest {
    @Test
    public void testTypeEquals() {
        assertEquals(new IntType(),
                     new IntType());
    }

    @Test
    public void testParseIntType() {
        final Token[] input = new Token[] {
            new IntToken()
        };
        assertEquals(new IntType(),
                     Parser.parseType(input));
    }
}
