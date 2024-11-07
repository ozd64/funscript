package com.cemozden.funscript.lexer;

public record Ident(String s) implements Token {

    @Override
    public String toString() {
        return "identifier " + s;
    }
}
