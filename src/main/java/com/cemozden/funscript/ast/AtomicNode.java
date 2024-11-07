package com.cemozden.funscript.ast;

public record AtomicNode<T>(T value) implements TokenTree {
}
