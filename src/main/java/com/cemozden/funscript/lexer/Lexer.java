package com.cemozden.funscript.lexer;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

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

        if (ch >= 'a' && ch <= 'z') {
           final Optional<Token> maybeIdentToken = getIdentToken();

           return maybeIdentToken
                   .map(this::mapToKeyword);
        } else if (ch == '"') {
           // String parsing
        } else if (ch >= '0' && ch <= '9'){
           //Integer parsing
        }

        return Optional.empty();
    }

    private Optional<Token> getIdentToken() {
        final int startOffset = offset;

        while (offset < codeChars.length && codeChars[offset] != ' ' && codeChars[offset] != '\n'){
            offset++;
        }

        if (offset < codeChars.length) {
            final char[] identifierChArray = Arrays.copyOfRange(codeChars, startOffset, offset);
            final String identifier = new String(identifierChArray);

            return Optional.of(new Ident(identifier));
        } else {
            return Optional.empty();
        }

    }

    private Token mapToKeyword(Token token) {
        if (token instanceof Ident ident) {
            return Optional
                    .<Token>ofNullable(KEYWORDS.get(ident.s()))
                    .orElse(token);
        } else {
            return token;
        }
    }

    private static final Map<String, Keyword> KEYWORDS = Map.of(
            "let", Keyword.LET
    );

}
