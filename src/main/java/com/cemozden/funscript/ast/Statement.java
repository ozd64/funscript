package com.cemozden.funscript.ast;

public sealed interface Statement extends TokenTree permits AtomicNode, BetweenLoop, Block, Operation, Variable {
}
