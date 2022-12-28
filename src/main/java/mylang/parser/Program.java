package mylang.parser;

import java.util.List;

public class Program {
    public final List<Stmt> statements;

    public Program(final List<Stmt> statements) {
        this.statements = statements;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof Program) {
            return statements.equals(((Program)other).statements);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return statements.hashCode();
    }

    @Override
    public String toString() {
        return "Program(" + statements.toString() + ")";
    }
}
