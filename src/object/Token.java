package object;

import java.util.ArrayList;
import java.util.List;

public enum Token {
    EMERALD,
    SAPPHIRE,
    RUBY,
    DIAMOND,
    ONYX,
    GOLD;

    public static Token[] cardValues() {
        return new Token[] { EMERALD, SAPPHIRE, RUBY, DIAMOND, ONYX };
    }
}
