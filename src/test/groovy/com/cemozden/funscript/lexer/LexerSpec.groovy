package com.cemozden.funscript.lexer

import spock.lang.Specification

class LexerSpec extends Specification {

    def "should lex a variable definition successfully"() {
        given: 'Variable definition and lexer'
        def var1 = "let x = 5;"
        def lexer = new Lexer(var1)

        when: 'variable gets lexec'
        def varKeyword = lexer.next().get()
        def varIdentifier = lexer.next().get()
        def equalOperator = lexer.next().get()
        def valueInt = lexer.next().get()

        then:
        varKeyword == Keyword.VAR
        varIdentifier == new Ident("x")
        equalOperator == Operator.EQUAL
        valueInt == new Ident(5)
    }

}
