package mylang.parser;

import java.util.List;

public class PrognStmt implements Stmt {
    public final List<Stmt> stmts;

    public PrognStmt(final List<Stmt> stmts) {
        this.stmts = stmts;
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof PrognStmt &&
                stmts.equals(((PrognStmt)other).stmts));
    }

    @Override
    public int hashCode() {
        return stmts.hashCode();
    }

    @Override
    public String toString() {
        return "PrognStmt(" + stmts.toString() + ")";
    }
}
