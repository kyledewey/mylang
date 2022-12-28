package mylang.tokenizer;

public class IntToken implements Token {
    // default constructor:
    // public IntToken() {
    //   super();
    // }
    
    @Override
    public boolean equals(final Object other) {
        return other instanceof IntToken;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "IntToken";
    }
}
