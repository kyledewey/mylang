package mylang.parser;

import mylang.tokenizer.*;

import java.util.List;
import java.util.ArrayList;

public class Parser {
    private final Token[] tokens;
    
    public Parser(final Token[] tokens) {
        this.tokens = tokens;
    } // Parser

    public Token getToken(final int position) throws ParseException {
        if (position >= 0 && position < tokens.length) {
            return tokens[position];
        } else {
            throw new ParseException("Out of tokens");
        }
    } // getToken

    public void assertTokenIs(final int position,
                              final Token expected) throws ParseException {
        final Token received = getToken(position);
        if (!expected.equals(received)) {
            throw new ParseException("Expected: " + expected.toString() +
                                     ", received: " + received.toString());
        }
    } // assertTokenIs

    public static Program parseProgram(final Token[] tokens) throws ParseException {
        final Parser parser = new Parser(tokens);
        final ParseResult<Program> program = parser.parseProgram(0);
        if (program.nextPosition == tokens.length) {
            return program.result;
        } else {
            throw new ParseException("Remaining tokens at end, starting with: " +
                                     parser.getToken(program.nextPosition).toString());
        }
    } // parseProgram
    
    // program ::= statement*
    // program ::= epsilon | statement program
    public ParseResult<Program> parseProgram(int position) throws ParseException {
        final List<Stmt> stmts = new ArrayList<Stmt>();
        boolean shouldRun = true;
        while (shouldRun) {
            try {
                final ParseResult<Stmt> stmt = parseStmt(position);
                position = stmt.nextPosition;
                stmts.add(stmt.result);
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return new ParseResult<Program>(new Program(stmts),
                                        position);
    } // parseProgram
    
    // expression ::= num | `true` | `false` | var |
    //                `(` op expression expression `)`
    public ParseResult<Exp> parseExp(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof NumberToken) {
            return new ParseResult<Exp>(new NumberLiteralExp(((NumberToken)token).value),
                                        position + 1);
        } else if (token instanceof TrueToken) {
            return new ParseResult<Exp>(new BooleanLiteralExp(true),
                                        position + 1);
        } else if (token instanceof FalseToken) {
            return new ParseResult<Exp>(new BooleanLiteralExp(false),
                                        position + 1);
        } else if (token instanceof IdentifierToken) {
            return new ParseResult<Exp>(new VariableExp(new Variable(((IdentifierToken)token).name)),
                                        position + 1);
        } else if (token instanceof LeftParenToken) {
            final ParseResult<Op> op = parseOp(position + 1);
            final ParseResult<Exp> left = parseExp(op.nextPosition);
            final ParseResult<Exp> right = parseExp(left.nextPosition);
            assertTokenIs(right.nextPosition, new RightParenToken());
            return new ParseResult<Exp>(new BinaryOperatorExp(op.result,
                                                              left.result,
                                                              right.result),
                                        right.nextPosition + 1);
        } else {
            throw new ParseException("expected expression; received: " + token.toString());
        }
    } // parseExp
                                        
    // op ::= `+` | `-` | `&&` | `||` | `<`
    public ParseResult<Op> parseOp(final int position) throws ParseException {
        final Token token = getToken(position);
        Op op = null;
        if (token instanceof PlusToken) {
            op = new PlusOp();
        } else if (token instanceof MinusToken) {
            op = new MinusOp();
        } else if (token instanceof LogicalAndToken) {
            op = new LogicalAndOp();
        } else if (token instanceof LogicalOrToken) {
            op = new LogicalOrOp();
        } else if (token instanceof LessThanToken) {
            op = new LessThanOp();
        } else {
            throw new ParseException("Expected operator; received: " + token.toString());
        }

        assert(op != null);
        return new ParseResult<Op>(op, position + 1);
    } // parseOp
      
    // var is a Variable
    public ParseResult<Variable> parseVariable(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof IdentifierToken) {
            return new ParseResult<Variable>(new Variable(((IdentifierToken)token).name),
                                             position + 1);
        } else {
            throw new ParseException("Expected variable; received: " + token.toString());
        }
    } // parseVariable
    
    // assign ::= `(` `=` var expression `)`
    public ParseResult<Stmt> parseAssign(final int position) throws ParseException {
        assertTokenIs(position, new LeftParenToken());
        assertTokenIs(position + 1, new SingleEqualsToken());
        final ParseResult<Variable> variable = parseVariable(position + 2);
        final ParseResult<Exp> exp = parseExp(variable.nextPosition);
        assertTokenIs(exp.nextPosition, new RightParenToken());
        return new ParseResult<Stmt>(new AssignStmt(variable.result,
                                                    exp.result),
                                     exp.nextPosition + 1);
    } // parseAssign
    
    // loop ::= `(` `while` expression statement `)`
    public ParseResult<Stmt> parseLoop(final int position) throws ParseException {
        assertTokenIs(position, new LeftParenToken());
        assertTokenIs(position + 1, new WhileToken());
        final ParseResult<Exp> guard = parseExp(position + 2);
        final ParseResult<Stmt> body = parseStmt(guard.nextPosition);
        assertTokenIs(body.nextPosition, new RightParenToken());
        return new ParseResult<Stmt>(new WhileStmt(guard.result,
                                                   body.result),
                                     body.nextPosition + 1);
    } // parseLoop

    // vardec ::= `(` `vardec` type var expression `)`
    public ParseResult<Stmt> parseVardec(final int position) throws ParseException {
        assertTokenIs(position, new LeftParenToken());
        assertTokenIs(position + 1, new VardecToken());
        final ParseResult<Type> type = parseType(position + 2);
        final ParseResult<Variable> variable = parseVariable(type.nextPosition);
        final ParseResult<Exp> exp = parseExp(variable.nextPosition);
        assertTokenIs(exp.nextPosition, new RightParenToken());
        return new ParseResult<Stmt>(new VardecStmt(type.result,
                                                    variable.result,
                                                    exp.result),
                                     exp.nextPosition + 1);
    } // parseVardec

    // statement ::= vardec | loop | assign
    public ParseResult<Stmt> parseStmt(final int position) throws ParseException {
        try {
            return parseVardec(position);
        } catch (final ParseException e1) {
            try {
                return parseLoop(position);
            } catch (final ParseException e2) {
                return parseAssign(position);
            }
        }
    } // parseStmt
               
    // type ::= `int` | `bool`
    public ParseResult<Type> parseType(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof IntToken) {
            return new ParseResult<Type>(new IntType(), position + 1);
        } else if (token instanceof BoolToken) {
            return new ParseResult<Type>(new BoolType(), position + 1);
        } else {
            throw new ParseException("Expected type; received: " +
                                     token.toString());
        }
    } // parseType
} // Parser
