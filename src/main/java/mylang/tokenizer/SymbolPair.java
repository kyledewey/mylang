package mylang.tokenizer;

public class SymbolPair {
    public final String asString;
    public final Token asToken;

    public SymbolPair(final String asString,
                      final Token asToken) {
        this.asString = asString;
        this.asToken = asToken;
    }
}
