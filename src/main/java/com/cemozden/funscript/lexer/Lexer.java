package com.cemozden.funscript.lexer;

import java.util.*;

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

    private static final Map<String, Operator> OPERATORS = Map.of(
            "=", Operator.EQUAL,
            ";", Operator.SEMICOLON,
            ":", Operator.COLON,
            "+", Operator.PLUS,
            "{", Operator.OPEN_CURLY_BRACKET,
            "}", Operator.CLOSED_CURLY_BRACKET,
            "(", Operator.OPEN_PARANTHESES,
            ")", Operator.CLOSED_PARANTHESES
    );

}
