package mylang.tokenizer;

public class PrintToken implements Token {
    @Override
    public boolean equals(final Object other) {
        return other instanceof PrintToken;
    }

    @Override
    public int hashCode() {
        return 14;
    }

    @Override
    public String toString() {
        return "PrintToken";
    }
}
