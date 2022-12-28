package mylang.tokenizer;

public class LogicalOrToken implements Token {
    @Override
    public boolean equals(final Object other) {
        return other instanceof LogicalOrToken;
    }

    @Override
    public int hashCode() {
        return 12;
    }

    @Override
    public String toString() {
        return "LogicalOrToken";
    }
}
