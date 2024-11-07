package com.cemozden.funscript.lexer;

public enum Keyword implements Token {
    LET,
    BETWEEN,
    BY,
    TO;

    @Override
    public String toString() {
        return switch (this) {
            case LET -> "let";
            case BETWEEN -> "between";
            case BY -> "by";
            case TO -> "to";
        };
    }
}
