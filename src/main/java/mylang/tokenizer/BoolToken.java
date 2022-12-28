package mylang.tokenizer;

public class BoolToken implements Token {
    @Override
    public boolean equals(final Object other) {
        return other instanceof BoolToken;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "BoolToken";
    }
}
