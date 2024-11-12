package com.cemozden.funscript.ast;

import com.cemozden.funscript.lexer.Operator;

import java.util.Optional;

public record AtomicNode<T>(T value) implements TokenTree, Statement, Expression<T> {

    @Override
    public <L> Optional<Expression<L>> lhs() {
        return Optional.empty();
    }

    @Override
    public Optional<Operator> operator() {
        return Optional.empty();
    }

    @Override
    public <R> Optional<Expression<R>> rhs() {
        return Optional.empty();
    }

    @Override
    public T get() {
        return value;
    }
}
