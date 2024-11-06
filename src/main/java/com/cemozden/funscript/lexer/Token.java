package com.cemozden.funscript.lexer;

public sealed interface Token permits AtomicIdent, Keyword, Operator, Ident {
}

record Ident(String s) implements Token {

}

record AtomicIdent<T>(T value) implements Token {
}

enum Keyword implements Token {
    LET,
    TRUE,
    BETWEEN, BY, TO, FALSE
}

enum Operator implements Token {
    EQUAL,
    COLON, PLUS, OPEN_CURLY_BRACKET, CLOSED_CURLY_BRACKET, OPEN_PARANTHESES, CLOSED_PARANTHESES, SEMICOLON
}
