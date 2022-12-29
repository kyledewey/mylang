package mylang.typechecker;

import java.util.Map;
import java.util.HashMap;

import mylang.parser.*;

public class Typechecker {
    public static Type typecheckBin(final BinaryOperatorExp exp,
                                    final Map<Variable, Type> typeEnv)
        throws TypeErrorException {
        final Type leftType = typecheckExp(exp.left, typeEnv);
        final Type rightType = typecheckExp(exp.right, typeEnv);
        if ((exp.op instanceof PlusOp ||
             exp.op instanceof MinusOp) &&
            leftType instanceof IntType &&
            rightType instanceof IntType) {
            return new IntType();
        } else if ((exp.op instanceof LogicalAndOp ||
                    exp.op instanceof LogicalOrOp) &&
                   leftType instanceof BoolType &&
                   rightType instanceof BoolType) {
            return new BoolType();
        } else if (exp.op instanceof LessThanOp &&
                   leftType instanceof IntType &&
                   rightType instanceof IntType) {
            return new BoolType();
        } else {
            throw new TypeErrorException("ill-typed binary expression: " + exp.toString());
        }
    } // typecheckBin
            
    public static Type typecheckExp(final Exp exp,
                                    final Map<Variable, Type> typeEnv)
        throws TypeErrorException {
        if (exp instanceof NumberLiteralExp) {
            return new IntType();
        } else if (exp instanceof BooleanLiteralExp) {
            return new BoolType();
        } else if (exp instanceof VariableExp) {
            final Variable variable = ((VariableExp)exp).variable;
            if (typeEnv.containsKey(variable)) {
                return typeEnv.get(variable);
            } else {
                throw new TypeErrorException("Variable not in scope: " + variable.toString());
            }
        } else if (exp instanceof BinaryOperatorExp) {
            return typecheckBin((BinaryOperatorExp)exp, typeEnv);
        } else {
            assert(false);
            throw new TypeErrorException("Unrecognized expression: " + exp.toString());
        }
    } // typecheckExp

    public static Map<Variable, Type> addToMap(final Map<Variable, Type> typeEnv,
                                               final Variable variable,
                                               final Type type) {
        final Map<Variable, Type> retval = new HashMap<Variable, Type>();
        retval.putAll(typeEnv);
        retval.put(variable, type);
        return retval;
    } // addToMap

    public static void assertTypesEqual(final Type expected,
                                        final Type received) throws TypeErrorException {
        if (!expected.equals(received)) {
            throw new TypeErrorException("Expected type: " + expected.toString() +
                                         ", received type: " + received.toString());
        }
    } // assertTypesEqual

    public static Map<Variable, Type> typecheckWhile(final WhileStmt stmt,
                                                     final Map<Variable, Type> typeEnv)
        throws TypeErrorException {
        assertTypesEqual(new BoolType(),
                         typecheckExp(stmt.guard, typeEnv));
        typecheckStmt(stmt.body, typeEnv);
        return typeEnv;
    } // typecheckWhile
    
    // (vardec x bool true)
    // (while (< 2 3)
    //   (vardec x int 7)
    //   ...)
    public static Map<Variable, Type> typecheckVardec(final VardecStmt stmt,
                                                      final Map<Variable, Type> typeEnv)
        throws TypeErrorException {
        final Type receivedType = typecheckExp(stmt.exp, typeEnv);
        assertTypesEqual(stmt.type, receivedType);
        return addToMap(typeEnv, stmt.variable, receivedType);
    } // typecheckVardec

    public static Map<Variable, Type> typecheckAssign(final AssignStmt stmt,
                                                      final Map<Variable, Type> typeEnv)
        throws TypeErrorException {
        if (typeEnv.containsKey(stmt.variable)) {
            final Type expected = typeEnv.get(stmt.variable);
            assertTypesEqual(expected,
                             typecheckExp(stmt.exp, typeEnv));
            return typeEnv;
        } else {
            throw new TypeErrorException("Variable not in scope: " + stmt.variable.toString());
        }
    } // typecheckAssign

    public static Map<Variable, Type> typecheckPrint(final PrintStmt stmt,
                                                     final Map<Variable, Type> typeEnv)
        throws TypeErrorException {
        typecheckExp(stmt.exp, typeEnv);
        return typeEnv;
    }

    public static Map<Variable, Type> typecheckProgn(final PrognStmt progn,
                                                     final Map<Variable, Type> initialTypeEnv)
        throws TypeErrorException {
        Map<Variable, Type> typeEnv = initialTypeEnv;
        for (final Stmt stmt : progn.stmts) {
            typeEnv = typecheckStmt(stmt, typeEnv);
        }
        return initialTypeEnv;
    }
    
    public static Map<Variable, Type> typecheckStmt(final Stmt stmt,
                                                    final Map<Variable, Type> typeEnv)
        throws TypeErrorException {
        if (stmt instanceof VardecStmt) {
            return typecheckVardec((VardecStmt)stmt, typeEnv);
        } else if (stmt instanceof WhileStmt) {
            return typecheckWhile((WhileStmt)stmt, typeEnv);
        } else if (stmt instanceof AssignStmt) {
            return typecheckAssign((AssignStmt)stmt, typeEnv);
        } else if (stmt instanceof PrintStmt) {
            return typecheckPrint((PrintStmt)stmt, typeEnv);
        } else if (stmt instanceof PrognStmt) {
            return typecheckProgn((PrognStmt)stmt, typeEnv);
        } else {
            assert(false);
            throw new TypeErrorException("Unrecognized statement: " + stmt.toString());
        }
    } // typecheckStmt

    public static void typecheckProgram(final Program program) throws TypeErrorException {
        typecheckStmt(program.stmt, new HashMap<Variable, Type>());
    } // typecheckProgram
} // Typechecker
