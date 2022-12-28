package mylang.tokenizer;

public class VardecToken implements Token {
    @Override
    public boolean equals(final Object other) {
        return other instanceof VardecToken;
    }

    @Override
    public int hashCode() {
        return 3;
    }

    @Override
    public String toString() {
        return "VardecToken";
    }
}
