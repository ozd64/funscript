package com.cemozden.funscript.ast;

import com.cemozden.funscript.lexer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Parser {

    private final Lexer lexer;

    public Parser(String code) {
        this.lexer = new Lexer(code);
    }

    public TokenTree parseAST() throws SyntaxErrorException {
        Optional<Token> maybeNextToken;
        final List<Statement> statements = new ArrayList<>(100);

        while ((maybeNextToken = lexer.nextToken()).isPresent()) {
           final Token token = maybeNextToken.get();

           final Statement statement = switch (token) {
               case Keyword.LET -> {
                   final Ident variableName = lexer.expectToken(
                           Ident.class,
                           unexpectedToken -> "Expected identifier got " + unexpectedToken
                   );
                   lexer.expectOperator(
                           Operator.EQUAL,
                           unexpectedToken -> "Expected = got " + unexpectedToken
                   );

                   final var expression = parseExpression();

                   //TODO: Add Expression type that returns the type of the expression
                   yield new Variable(variableName, expression);
               }
               case Ident ident -> {
                    final Operator op = lexer.expectOperator(t -> "Expected operator found " + t);

                    yield switch (op) {
                        case Operator.SEMICOLON -> new AtomicNode<>(ident);
                        default -> new Operation<>(new AtomicNode<>(ident), op, parseExpression());
                    };
               }
               default -> throw new SyntaxErrorException("Unimplemented AST node for token " + token);
           };

           statements.add(statement);
        }

        return new Block(statements);
    }

    private Expression<?> parseExpression() throws SyntaxErrorException {
        final Optional<Token> maybeToken = lexer.nextToken();

        if (maybeToken.isEmpty()) {
            throw new SyntaxErrorException("No symbol found to parse expression.");
        }

        final Token token = maybeToken.get();

        final var lhs = switch (token) {
            case AtomicIdent ident -> new AtomicNode<>(ident.value());
            case Ident ident -> new AtomicNode<>(ident);
            case Operator.OPEN_PARENTHESES -> parseExpression();
            default -> throw new SyntaxErrorException("Unexpected token for an expression");
        };

        final Operator op = lexer.expectOperator(t -> "Expected operator found " + t);

        return switch (op) {
            case SEMICOLON, CLOSED_PARENTHESES -> lhs;
            case PLUS -> new Operation<>(lhs, Operator.PLUS, parseExpression());
            case MINUS -> new Operation<>(lhs, Operator.MINUS, parseExpression());
            case STAR -> new Operation<>(lhs, Operator.STAR, parseExpression());
            case SLASH -> new Operation<>(lhs, Operator.SLASH, parseExpression());
            default -> throw new SyntaxErrorException("Expected expression operator found " + op);
        };
    }

}
