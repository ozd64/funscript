package com.cemozden.funscript.lexer;

public sealed interface Token permits Ident, Keyword, Operator {
}

record Ident<T>(T value) implements Token {

}

enum Keyword implements Token {
    VAR
}

enum Operator implements Token {
    EQUAL
}
