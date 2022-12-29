package mylang.typechecker;

import mylang.parser.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TypecheckerTest {
    public static final Map<Variable, Type> EMPTY_TYPE_ENV =
        new HashMap<Variable, Type>();
    
    @Test
    public void typecheckNum() throws TypeErrorException {
        assertEquals(new IntType(),
                     Typechecker.typecheckExp(new NumberLiteralExp(7),
                                              EMPTY_TYPE_ENV));
    }

    @Test
    public void typecheckBool() throws TypeErrorException {
        assertEquals(new BoolType(),
                     Typechecker.typecheckExp(new BooleanLiteralExp(true),
                                              EMPTY_TYPE_ENV));
    }

    // (vardec int x 0)
    // (vardec int y (+ x x))
    @Test
    public void typecheckVardecs() throws TypeErrorException {
        final List<Stmt> stmts = new ArrayList<Stmt>();
        stmts.add(new VardecStmt(new IntType(),
                                 new Variable("x"),
                                 new NumberLiteralExp(0)));
        stmts.add(new VardecStmt(new IntType(),
                                 new Variable("y"),
                                 new BinaryOperatorExp(new PlusOp(),
                                                       new VariableExp(new Variable("x")),
                                                       new VariableExp(new Variable("x")))));
        Typechecker.typecheckProgram(new Program(new PrognStmt(stmts)));
    }

    // (vardec int x 0)
    // (while (< x 10)
    //   (= x (+ x 1)))
    @Test
    public void typecheckWhile() throws TypeErrorException {
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
        Typechecker.typecheckProgram(new Program(new PrognStmt(stmts)));
    }

    // - Variable not in scope
    // (vardec int x y)
    @Test(expected = TypeErrorException.class)
    public void testVariableNotInScope() throws TypeErrorException {
        final List<Stmt> stmts = new ArrayList<Stmt>();
        stmts.add(new VardecStmt(new IntType(),
                                 new Variable("x"),
                                 new VariableExp(new Variable("y"))));
        Typechecker.typecheckProgram(new Program(new PrognStmt(stmts)));
    }

    // - Expression not of correct type
    // (vardec int x true)
    @Test(expected = TypeErrorException.class)
    public void testVariableNotOfCorrectTypeInVardec() throws TypeErrorException {
        final List<Stmt> stmts = new ArrayList<Stmt>();
        stmts.add(new VardecStmt(new IntType(),
                                 new Variable("x"),
                                 new BooleanLiteralExp(true)));
        Typechecker.typecheckProgram(new Program(new PrognStmt(stmts)));
    }

    // -Boolean and
    // (vardec bool b (&& true false))
    @Test
    public void testBooleanAnd() throws TypeErrorException {
        final List<Stmt> stmts = new ArrayList<Stmt>();
        stmts.add(new VardecStmt(new BoolType(),
                                 new Variable("b"),
                                 new BinaryOperatorExp(new LogicalAndOp(),
                                                       new BooleanLiteralExp(true),
                                                       new BooleanLiteralExp(false))));
        Typechecker.typecheckProgram(new Program(new PrognStmt(stmts)));
    }

    // -Assignment to a variable that doesn't exist
    // (= x true)
    @Test(expected = TypeErrorException.class)
    public void testAssignmentToNonExistentVariable() throws TypeErrorException {
        final List<Stmt> stmts = new ArrayList<Stmt>();
        stmts.add(new AssignStmt(new Variable("x"),
                                 new BooleanLiteralExp(true)));
        Typechecker.typecheckProgram(new Program(new PrognStmt(stmts)));
    }        
}
