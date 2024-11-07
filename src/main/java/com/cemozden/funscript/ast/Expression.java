package com.cemozden.funscript.ast;

import com.cemozden.funscript.lexer.Operator;

public record Expression(TokenTree lhs, Operator operator, TokenTree rhs) implements TokenTree {
}
