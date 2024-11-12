package com.cemozden.funscript.ast;

import com.cemozden.funscript.lexer.Operator;

import java.util.Optional;

public sealed interface Expression<T> extends TokenTree permits AtomicNode, Operation {
    <L> Optional<Expression<L>> lhs();
    Optional<Operator> operator();
    <R> Optional<Expression<R>> rhs();
    T get();
}
