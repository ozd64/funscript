package com.cemozden.funscript.lexer;

import com.cemozden.funscript.ast.SyntaxErrorException;

import java.util.*;
import java.util.function.Function;

public class Lexer {

    private final char[] codeChars;
    private int offset = 0;

    public Lexer(String code) {
        this.codeChars = code.toCharArray();
    }

    public Optional<Token> nextToken() {
        while (offset < codeChars.length && (codeChars[offset] == ' ' || codeChars[offset] == '\n')){
           offset++;
        }

        if (offset >= codeChars.length)
            return Optional.empty();

        final char ch = codeChars[offset];

        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
            return getIdentToken();
        } else if (ch == '"') {
           return getStringLiteral();
        } else if (ch >= '0' && ch <= '9'){
           return getNumberLiteral();
        } else {
            return getOperatorToken();
        }
    }

    public <T extends Token> T expectToken(Class<T> tokenType, Function<Token, String> unexpectedTokenMessageSupplier) throws SyntaxErrorException {
        final Optional<Token> nextToken = this.nextToken();

        if (nextToken.isEmpty()) {
            throw new SyntaxErrorException("No symbol found.");
        }

        final Token token = nextToken.get();

        if (tokenType.isInstance(token)) {
           return (T) token;
        } else throw new SyntaxErrorException(unexpectedTokenMessageSupplier.apply(token));
    }

    public Keyword expectKeyword(Keyword expectedKeyword, Function<Token, String> unexpectedKeywordSupplier) throws SyntaxErrorException {
        final Optional<Token> nextToken = this.nextToken();

        if (nextToken.isEmpty()) {
            throw new SyntaxErrorException("No symbol found.");
        }

        final Token token = nextToken.get();

        if (expectedKeyword.getClass().isInstance(token) && token == expectedKeyword)
            return expectedKeyword;
        else throw new SyntaxErrorException(unexpectedKeywordSupplier.apply(token));
    }

    public Operator expectOperator(Operator expectedOperator, Function<Token, String> unexpectedOperatorSupplier) throws SyntaxErrorException {
        final Optional<Token> nextToken = this.nextToken();

        if (nextToken.isEmpty()) {
            throw new SyntaxErrorException("No symbol found.");
        }

        final Token token = nextToken.get();

        if (expectedOperator.getClass().isInstance(token) && token == expectedOperator)
            return expectedOperator;
        else throw new SyntaxErrorException(unexpectedOperatorSupplier.apply(token));
    }

    public Operator expectOperator(Function<Token, String> unexpectedTokenSupplier) throws SyntaxErrorException {
        final Optional<Token> maybeToken = this.nextToken();

        if (maybeToken.isEmpty()) {
            throw new SyntaxErrorException("No symbol found.");
        }

        final Token token = maybeToken.get();


        if (token instanceof Operator)
            return (Operator) token;
        else
            throw new SyntaxErrorException(unexpectedTokenSupplier.apply(token));
    }

    private Optional<Token> getIdentToken() {
        final int startOffset = offset;

        while ( offset < codeChars.length &&
                codeChars[offset] != ' ' &&
                codeChars[offset] != '\n' &&
                !SINGLE_CHAR_OPERATORS.contains(codeChars[offset])
        )
            offset++;

        if (offset <= codeChars.length) {
            final char[] identifierChArray = Arrays.copyOfRange(codeChars, startOffset, offset);
            final String identifier = new String(identifierChArray);

            final Optional<Token> maybeKeyword = Optional.ofNullable(KEYWORDS.get(identifier));

            if (maybeKeyword.isPresent())
                return maybeKeyword;
            else if (identifier.equals("true") || identifier.equals("false"))
                return Optional.of(new AtomicIdent<>(Boolean.valueOf(identifier)));
            else
                return Optional.of(new Ident(identifier));
        } else
            return Optional.empty();
        
    }

    private Optional<Token> getStringLiteral(){
       final int startOffset = ++offset;

       while (offset < codeChars.length && codeChars[offset] != '"')
           offset++;

       if (offset <= codeChars.length) {
           final char[] numberChArray = Arrays.copyOfRange(codeChars, startOffset, offset);
           final String stringLiteral = new String(numberChArray);

           offset++;
           return Optional.of(new AtomicIdent<>(stringLiteral));
       } else
           return Optional.empty();

    }

    private Optional<Token> getNumberLiteral() {
        final int startOffset = offset;

        while (offset < codeChars.length && (codeChars[offset] >= '0' && codeChars[offset] <= '9' && codeChars[offset] != '.'))
            offset++;

        if (offset <= codeChars.length) {
            if (codeChars[offset] == '.') {
                do offset++;
                while (offset < codeChars.length && (codeChars[offset] >= '0' && codeChars[offset] <= '9'));

                final char[] floatingPointNumberStrChr = Arrays.copyOfRange(codeChars, startOffset, offset);
                final String floatingPointNumberStr = new String(floatingPointNumberStrChr);

                return Optional.of(new AtomicIdent<>(Double.valueOf(floatingPointNumberStr)));
            } else {
                final char[] numberChArray = Arrays.copyOfRange(codeChars, startOffset, offset);
                final Integer number = Integer.parseInt(new String(numberChArray));

                return Optional.of(new AtomicIdent<>(number));
            }
        } else
            return Optional.empty();
    }

    private Optional<Token> getOperatorToken() {
        final int startOffset = offset;

        while (
                offset < codeChars.length &&
                        SINGLE_CHAR_OPERATORS.contains(codeChars[offset]) &&
                        codeChars[offset] != ' ' &&
                        codeChars[offset] != '\n'
        )
            offset++;

        if (offset <= codeChars.length) {
            final char[] operatorChArray = Arrays.copyOfRange(codeChars, startOffset, offset);
            final String operatorString = new String(operatorChArray);

            return Optional.ofNullable(OPERATORS.get(operatorString));
        } else {
            return Optional.empty();
        }
    }

    private static final Map<String, Keyword> KEYWORDS = Map.of(
            "let", Keyword.LET,
            "between", Keyword.BETWEEN,
            "by", Keyword.BY,
            "to", Keyword.TO
    );

    private static final Set<Character> SINGLE_CHAR_OPERATORS = Set.of('+', '-', '*', '/', ';', '=', ':', '{', '}', '(', ')');

    private static final Map<String, Operator> OPERATORS = new HashMap<>();

    static {
        OPERATORS.put(";", Operator.SEMICOLON);
        OPERATORS.put(":", Operator.COLON);
        OPERATORS.put("+", Operator.PLUS);
        OPERATORS.put("-", Operator.MINUS);
        OPERATORS.put("*", Operator.STAR);
        OPERATORS.put("/", Operator.SLASH);
        OPERATORS.put("{", Operator.OPEN_CURLY_BRACKET);
        OPERATORS.put("}", Operator.CLOSED_CURLY_BRACKET);
        OPERATORS.put("(", Operator.OPEN_PARENTHESES);
        OPERATORS.put(")", Operator.CLOSED_PARENTHESES);
        OPERATORS.put("=", Operator.EQUAL);
    }

}
