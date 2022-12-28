package mylang.tokenizer;

public class WhileToken implements Token {
    @Override
    public boolean equals(final Object other) {
        return other instanceof WhileToken;
    }

    @Override
    public int hashCode() {
        return 7;
    }

    @Override
    public String toString() {
        return "WhileToken";
    }
}
