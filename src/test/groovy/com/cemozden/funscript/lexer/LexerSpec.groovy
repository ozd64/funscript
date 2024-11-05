package com.cemozden.funscript.lexer

import spock.lang.Specification

class LexerSpec extends Specification {

    def "should lex a variable definition successfully"() {
        given: 'Variable definition and lexer'
        def var1 = "let x = 5;"
        def lexer = new Lexer(var1)

        when: 'variable gets lexec'
        def letKeyword = lexer.next().get()
        def variableName = lexer.next().get()
        //def equalOperator = lexer.next().get()
        //def valueInt = lexer.next().get()

        then:
        letKeyword == Keyword.LET
        variableName == new Ident("x")
        //equalOperator == Operator.EQUAL
        //valueInt == new AtomicIdent(5)
    }

}
