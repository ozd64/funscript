package com.cemozden.funscript.ast;

import java.util.List;

public record Block(List<Statement> statements) implements TokenTree, Statement {
}
