package com.cemozden.funscript.ast;

import com.cemozden.funscript.lexer.Ident;

public record Variable(Ident name, TokenTree value) implements TokenTree, Statement {
}
