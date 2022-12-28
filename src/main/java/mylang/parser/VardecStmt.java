package mylang.parser;

public class VardecStmt implements Stmt {
    public final Type type;
    public final Variable variable;
    public final Exp exp;

    public VardecStmt(final Type type,
                      final Variable variable,
                      final Exp exp) {
        this.type = type;
        this.variable = variable;
        this.exp = exp;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof VardecStmt) {
            final VardecStmt otherVar = (VardecStmt)other;
            return (type.equals(otherVar.type) &&
                    variable.equals(otherVar.variable) &&                    
                    exp.equals(otherVar.exp));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (type.hashCode() +
                variable.hashCode() +
                exp.hashCode());
    }

    @Override
    public String toString() {
        return ("VardecStmt(" +
                type.toString() + ", " +
                variable.toString() + ", " +
                exp.toString() + ")");
    }
}
