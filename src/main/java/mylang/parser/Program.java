package mylang.parser;

import java.util.List;

public class Program {
    public final List<Stmt> stmts;

    public Program(final List<Stmt> stmts) {
        this.stmts = stmts;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof Program) {
            return stmts.equals(((Program)other).stmts);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return stmts.hashCode();
    }

    @Override
    public String toString() {
        return "Program(" + stmts.toString() + ")";
    }
}
