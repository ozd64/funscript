package com.cemozden.funscript.lexer;

import java.util.*;

public class Lexer {

    private final char[] codeChars;
    private int offset = 0;

    public Lexer(String code) {
        this.codeChars = code.toCharArray();
    }

    public Optional<Token> next() {
        while (offset < codeChars.length && (codeChars[offset] == ' ' || codeChars[offset] == '\n')){
           offset++;
        }

        final char ch = codeChars[offset];

        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
           final Optional<Token> maybeIdentToken = getIdentToken();

           return maybeIdentToken
                   .map(this::mapToKeyword);
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

        while (offset < codeChars.length && codeChars[offset] != ' ' && codeChars[offset] != '\n')
            offset++;

        if (offset <= codeChars.length) {
            final char[] operatorChArray = Arrays.copyOfRange(codeChars, startOffset, offset);
            final String operatorString = new String(operatorChArray);

            return Optional.ofNullable(OPERATORS.get(operatorString));
        } else {
            return Optional.empty();
        }
    }

    private Token mapToKeyword(Token token) {
        if (token instanceof Ident ident)
            return Optional
                    .<Token>ofNullable(KEYWORDS.get(ident.s()))
                    .orElse(token);
         else
            return token;

    }

    private static final Map<String, Keyword> KEYWORDS = Map.of(
            "let", Keyword.LET
    );

    private static final Set<Character> SINGLE_CHAR_OPERATORS = new HashSet<>();

    static {
        SINGLE_CHAR_OPERATORS.add('+');
        SINGLE_CHAR_OPERATORS.add('-');
        SINGLE_CHAR_OPERATORS.add('*');
        SINGLE_CHAR_OPERATORS.add('/');
        SINGLE_CHAR_OPERATORS.add(';');
        SINGLE_CHAR_OPERATORS.add('=');
        SINGLE_CHAR_OPERATORS.add(':');
    }

    private static final Map<String, Operator> OPERATORS = Map.of(
            "=", Operator.EQUAL,
            ";", Operator.SEMICOLON,
            ":", Operator.COLON
    );

}
