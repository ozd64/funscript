package com.cemozden.funscript.ast;

import com.cemozden.funscript.lexer.Ident;

public record BetweenLoop(Ident variable, int start, int end, Block executionBlock) implements Statement, TokenTree {
}
