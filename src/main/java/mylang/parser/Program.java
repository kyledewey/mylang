package mylang.parser;

public class Program {
    public final Stmt stmt;

    public Program(final Stmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Program &&
                stmt.equals(((Program)other).stmt));
    }

    @Override
    public int hashCode() {
        return stmt.hashCode();
    }

    @Override
    public String toString() {
        return "Program(" + stmt.toString() + ")";
    }
}
