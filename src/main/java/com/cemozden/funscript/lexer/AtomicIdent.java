package com.cemozden.funscript.lexer;

public record AtomicIdent<T>(T value) implements Token {
    @Override
    public String toString() {
        return "identifier " + value.toString();
    }
}
