package mylang.codegen;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

import mylang.parser.*;

public class CodeGen {
    private final BufferedWriter writer;

    public CodeGen(final File outputFile) throws IOException {
        writer = new BufferedWriter(new FileWriter(outputFile));
    }

    public void writeOp(final Op op) throws IOException, CodeGenException {
        String s = null;
        if (op instanceof PlusOp) {
            s = "+";
        } else if (op instanceof MinusOp) {
            s = "-";
        } else if (op instanceof LogicalAndOp) {
            s = "&&";
        } else if (op instanceof LogicalOrOp) {
            s = "||";
        } else if (op instanceof LessThanOp) {
            s = "<";
        } else {
            assert(false);
            throw new CodeGenException("Unknown op: " + op.toString());
        }
        assert(s != null);
        writer.write(s);
    }
    
    public void writeExp(final Exp exp) throws IOException, CodeGenException {
        if (exp instanceof NumberLiteralExp) {
            writer.write(Integer.toString(((NumberLiteralExp)exp).value));
        } else if (exp instanceof BooleanLiteralExp) {
            writer.write(Boolean.toString(((BooleanLiteralExp)exp).value));
        } else if (exp instanceof VariableExp) {
            writer.write(((VariableExp)exp).variable.name);
        } else if (exp instanceof BinaryOperatorExp) {
            // mylang: `(` op expression expression `)`
            // JavaScript: (expression op expresion)
            //
            // (* 2 (+ 3 4))
            // 2 * 3 + 4
            //
            // (+ 1 2)
            // (1 + 2)
            final BinaryOperatorExp asOp = (BinaryOperatorExp)exp;
            writer.write("(");
            writeExp(asOp.left);
            writer.write(" ");
            writeOp(asOp.op);
            writer.write(" ");
            writeExp(asOp.right);
            writer.write(")");
        } else {
            assert(false);
            throw new CodeGenException("Unknown expression: " + exp.toString());
        }
    }
    
    // `(` `vardec` type var expression `)`
    // let var = expression;
    public void writeVardec(final VardecStmt stmt) throws IOException, CodeGenException {
        writer.write("let ");
        writer.write(stmt.variable.name);
        writer.write(" = ");
        writeExp(stmt.exp);
        writer.write(";\n");
    }

    // `(` `while` expression statement `)`
    // while (expression) { statement }
    public void writeWhile(final WhileStmt stmt) throws IOException, CodeGenException {
        writer.write("while (");
        writeExp(stmt.guard);
        writer.write(") { ");
        writeStmt(stmt.body);
        writer.write(" }");
    }

    // `(` `print` expression `)`
    // console.log(expression);
    public void writePrint(final PrintStmt stmt) throws IOException, CodeGenException {
        writer.write("console.log(");
        writeExp(stmt.exp);
        writer.write(");");
    }

    // `(` `progn` statement* `)`
    // { statement* }
    public void writeProgn(final PrognStmt progn) throws IOException, CodeGenException {
        writer.write("{ ");
        for (final Stmt stmt : progn.stmts) {
            writeStmt(stmt);
        }
        writer.write(" }");
    }
    
    // variable = expression;
    public void writeAssign(final AssignStmt stmt) throws IOException, CodeGenException {
        writer.write(stmt.variable.name);
        writer.write(" = ");
        writeExp(stmt.exp);
        writer.write(";\n");
    }
    
    public void writeStmt(final Stmt stmt) throws IOException, CodeGenException {
        if (stmt instanceof VardecStmt) {
            writeVardec((VardecStmt)stmt);
        } else if (stmt instanceof WhileStmt) {
            writeWhile((WhileStmt)stmt);
        } else if (stmt instanceof AssignStmt) {
            writeAssign((AssignStmt)stmt);
        } else if (stmt instanceof PrintStmt) {
            writePrint((PrintStmt)stmt);
        } else if (stmt instanceof PrognStmt) {
            writeProgn((PrognStmt)stmt);
        } else {
            assert(false);
            throw new CodeGenException("Unknown statement: " + stmt.toString());
        }
    }
    
    public void writeProgram(final Program program) throws IOException, CodeGenException {
        writeStmt(program.stmt);
    }

    public void close() throws IOException {
        writer.close();
    }
    
    public static void writeProgram(final Program program,
                                    final File outputFile) throws IOException, CodeGenException {
        final CodeGen codegen = new CodeGen(outputFile);
        try {
            codegen.writeProgram(program);
        } finally {
            codegen.close();
        }
    }
}
