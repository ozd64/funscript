package com.cemozden.funscript.lexer;

public sealed interface Token permits AtomicIdent, Keyword, Operator, Ident {
}