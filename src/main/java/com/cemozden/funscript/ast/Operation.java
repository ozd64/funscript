package com.cemozden.funscript.ast;

import com.cemozden.funscript.lexer.Operator;

import java.util.Optional;

public record Operation<T, L, R>(Expression<L> left, Operator op, Expression<R> right) implements TokenTree, Expression<T>, Statement {

    @Override
    public Optional<Expression<L>> lhs() {
        return Optional.of(left);
    }

    @Override
    public Optional<Operator> operator() {
        return Optional.of(op);
    }

    @Override
    public T get() {
        return null;
    }

    @Override
    public Optional<Expression> rhs() {
        return Optional.of(right);
    }
}
