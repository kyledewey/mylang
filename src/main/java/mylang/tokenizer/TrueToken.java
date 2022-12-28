package mylang.tokenizer;

public class TrueToken implements Token {
    @Override
    public boolean equals(final Object other) {
        return other instanceof TrueToken;
    }

    @Override
    public int hashCode() {
        return 5;
    }

    @Override
    public String toString() {
        return "TrueToken";
    }
}
