package mylang.parser;

public class VariableExp implements Exp {
    public final Variable variable;

    public VariableExp(final Variable variable) {
        this.variable = variable;
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof VariableExp &&
                variable.equals(((VariableExp)other).variable));
    }

    @Override
    public int hashCode() {
        return variable.hashCode();
    }

    @Override
    public String toString() {
        return "VariableExp(" + variable.toString() + ")";
    }
}
