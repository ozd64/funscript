package com.cemozden.funscript.ast;

public sealed interface Statement extends TokenTree permits BetweenLoop, Variable, Block {
}
