package com.cemozden.funscript.ast;

public sealed interface TokenTree permits AtomicNode, BetweenLoop, Block, Expression, Statement, Variable {}

