package mylang.tokenizer;

public class NumberToken implements Token {
    public final int value;

    public NumberToken(final int value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof NumberToken &&
                value == ((NumberToken)other).value);
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return "NumberToken(" + value + ")";
    }
}
