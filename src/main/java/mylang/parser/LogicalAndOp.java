package mylang.parser;

public class LogicalAndOp implements Op {
    @Override
    public boolean equals(final Object other) {
        return other instanceof LogicalAndOp;
    }

    @Override
    public int hashCode() {
        return 4;
    }

    @Override
    public String toString() {
        return "LogicalAndOp";
    }
}
