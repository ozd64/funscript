package com.cemozden.funscript;

import com.cemozden.funscript.ast.Parser;
import com.cemozden.funscript.ast.SyntaxErrorException;
import com.cemozden.funscript.ast.TokenTree;

public class Interpreter {
    public static void main(String[] args) throws SyntaxErrorException {
        final var parser = new Parser("let x = (year * 10) + 10;\n" +
                "x = x * 2;");

        final TokenTree tokenTree = parser.parseAST();

        System.out.println(tokenTree);
    }
}