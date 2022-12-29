package mylang.parser;

import mylang.tokenizer.*;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ParserTest {
    @Test
    public void testTypeEquals() {
        assertEquals(new IntType(),
                     new IntType());
    }

    // int
    @Test
    public void testParseIntType() throws ParseException {
        final Token[] input = new Token[] {
            new IntToken()
        };
        final Parser parser = new Parser(input);
        assertEquals(new ParseResult<Type>(new IntType(), 1),
                     parser.parseType(0));
    }

    // (vardec int x 7)
    @Test
    public void testVardec() throws ParseException {
        final Token[] input = new Token[] {
            new LeftParenToken(),
            new VardecToken(),
            new IntToken(),
            new IdentifierToken("x"),
            new NumberToken(7),
            new RightParenToken()
        };
        final Parser parser = new Parser(input);
        assertEquals(new ParseResult<Stmt>(new VardecStmt(new IntType(),
                                                          new Variable("x"),
                                                          new NumberLiteralExp(7)),
                                           6),
                     parser.parseVardec(0));
    }

    // (progn
    //   (vardec int x 0)
    //   (while (< x 10)
    //     (= x (+ x 1))))
    @Test
    public void testParseProgram() throws TokenizerException, ParseException {
        final Token[] tokens = Tokenizer.tokenize("(progn (vardec int x 0)\n" +
                                                  "(while (< x 10)\n" +
                                                  "  (= x (+ x 1))))\n");
        final List<Stmt> stmts = new ArrayList<Stmt>();
        stmts.add(new VardecStmt(new IntType(),
                                 new Variable("x"),
                                 new NumberLiteralExp(0)));
        stmts.add(new WhileStmt(new BinaryOperatorExp(new LessThanOp(),
                                                      new VariableExp(new Variable("x")),
                                                      new NumberLiteralExp(10)),
                                new AssignStmt(new Variable("x"),
                                               new BinaryOperatorExp(new PlusOp(),
                                                                     new VariableExp(new Variable("x")),
                                                                     new NumberLiteralExp(1)))));
        
        final Parser parser = new Parser(tokens);
        assertEquals(new ParseResult<Program>(new Program(new PrognStmt(stmts)),
                                              26),
                     parser.parseProgram(0));
    }
}
