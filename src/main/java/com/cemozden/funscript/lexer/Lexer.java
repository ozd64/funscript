package com.cemozden.funscript.lexer;

import java.util.Optional;

public class Lexer {

    private final String code;

    public Lexer(String code) {
        this.code = code;
    }

    public Optional<Token> next() {
        return Optional.empty();
    }

}
