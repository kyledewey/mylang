package mylang.parser;

public class LogicalOrOp implements Op {
    @Override
    public boolean equals(final Object other) {
        return other instanceof LogicalOrOp;
    }

    @Override
    public int hashCode() {
        return 5;
    }

    @Override
    public String toString() {
        return "LogicalOrOp";
    }
}
