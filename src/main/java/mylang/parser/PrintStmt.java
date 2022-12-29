package mylang.parser;

public class PrintStmt implements Stmt {
    public final Exp exp;

    public PrintStmt(final Exp exp) {
        this.exp = exp;
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof PrintStmt &&
                exp.equals(((PrintStmt)other).exp));
    }

    @Override
    public int hashCode() {
        return exp.hashCode();
    }

    @Override
    public String toString() {
        return "PrintStmt(" + exp.toString() + ")";
    }
}
