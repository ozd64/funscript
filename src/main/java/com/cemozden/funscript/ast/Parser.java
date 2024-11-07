package com.cemozden.funscript.ast;

import com.cemozden.funscript.lexer.*;

import java.util.Optional;


public class Parser {

    private final Lexer lexer;

    public Parser(String code) {
        this.lexer = new Lexer(code);
    }

    public TokenTree parseAST() throws SyntaxErrorException {
        //TODO: Implement the AST tree

        Optional<Token> maybeNextToken = lexer.nextToken();

        while (lexer.nextToken().isPresent()) {
           final Token token = maybeNextToken.get();

           final var lhs = switch (token) {
               case Keyword.LET -> {
                   final Ident variableName = lexer.expectToken(
                           Ident.class,
                           unexpectedToken -> "Expected identifier got " + unexpectedToken
                   );
                   lexer.expectOperator(
                           Operator.EQUAL,
                           unexpectedToken -> "Expected = got " + unexpectedToken
                   );

                   //TODO: Add Expression type that returns the type of the expression
                   yield new AtomicNode<>(5);
               }
               default -> new AtomicNode<>(5);
           };

        }

        return null;
    }

}
