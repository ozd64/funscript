package com.cemozden.funscript.lexer;

public enum Operator implements Token {
    EQUAL,
    COLON,
    PLUS,
    OPEN_CURLY_BRACKET,
    CLOSED_CURLY_BRACKET,
    OPEN_PARENTHESES,
    CLOSED_PARENTHESES,
    SEMICOLON, MINUS, STAR, SLASH;

    @Override
    public String toString() {
        return switch (this) {
            case EQUAL -> "=";
            case COLON -> ":";
            case PLUS -> "+";
            case MINUS -> "-";
            case STAR -> "*";
            case SLASH -> "/";
            case OPEN_CURLY_BRACKET -> "{";
            case CLOSED_CURLY_BRACKET -> "}";
            case OPEN_PARENTHESES -> "(";
            case CLOSED_PARENTHESES -> ")";
            case SEMICOLON -> ";";
        };
    }
}
