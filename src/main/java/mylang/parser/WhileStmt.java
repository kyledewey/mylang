package mylang.parser;

public class WhileStmt implements Stmt {
    public final Exp guard;
    public final Stmt body;

    public WhileStmt(final Exp guard,
                     final Stmt body) {
        this.guard = guard;
        this.body = body;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof WhileStmt) {
            final WhileStmt asWhile = (WhileStmt)other;
            return (guard.equals(asWhile.guard) &&
                    body.equals(asWhile.body));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return guard.hashCode() + body.hashCode();
    }

    @Override
    public String toString() {
        return ("WhileStmt(" +
                guard.toString() + ", " +
                body.toString() + ")");
    }
}

        
