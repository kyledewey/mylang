package mylang.parser;

public class NumberLiteralExp implements Exp {
    public final int value;

    public NumberLiteralExp(final int value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof NumberLiteralExp &&
                value == ((NumberLiteralExp)other).value);
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return "NumberLiteralExp(" + value + ")";
    }
}
