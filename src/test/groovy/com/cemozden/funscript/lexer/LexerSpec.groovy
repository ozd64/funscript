package com.cemozden.funscript.lexer

import spock.lang.Specification

class LexerSpec extends Specification {

    def "should lex an integer variable definition successfully"() {
        given: 'Variable definition and lexer'
        def var1 = "let x = 5;"
        def lexer = new Lexer(var1)

        when: 'variable gets lexed'
        def letKeyword = lexer.next().get()
        def variableName = lexer.next().get()
        def equalOperator = lexer.next().get()
        def valueInt = lexer.next().get()
        def semicolonOperator = lexer.next().get()

        then:
        letKeyword == Keyword.LET
        variableName == new Ident("x")
        equalOperator == Operator.EQUAL
        valueInt == new AtomicIdent(5)
        semicolonOperator == Operator.SEMICOLON
    }

    def "should lex an integer variable with explicit type definition successfully"() {
        given: 'Variable definition and lexer'
        def var1 = "let x: Num = 5;"
        def lexer = new Lexer(var1)

        when: 'variable gets lexed'
        def letKeyword = lexer.next().get()
        def variableName = lexer.next().get()
        def colonOperator = lexer.next().get()
        def varType = lexer.next().get()
        def equalOperator = lexer.next().get()
        def valueInt = lexer.next().get()
        def semicolonOperator = lexer.next().get()

        then:
        letKeyword == Keyword.LET
        variableName == new Ident("x")
        colonOperator == Operator.COLON
        varType == new Ident("Num")
        equalOperator == Operator.EQUAL
        valueInt == new AtomicIdent(5)
        semicolonOperator == Operator.SEMICOLON
    }

    def "should lex a string variable definition successfully"() {
        given: 'Variable definition and lexer'
        def var1 = "let a_string = \"test\";"
        def lexer = new Lexer(var1)

        when: 'variable gets lexed'
        def letKeyword = lexer.next().get()
        def variableName = lexer.next().get()
        def equalOperator = lexer.next().get()
        def valueString = lexer.next().get()
        def semicolonOperator = lexer.next().get()

        then:
        letKeyword == Keyword.LET
        variableName == new Ident("a_string")
        equalOperator == Operator.EQUAL
        valueString == new AtomicIdent("test")
        semicolonOperator == Operator.SEMICOLON
    }

    def "should parse a floating point number variable definition successfully"() {
        given: 'Variable definition and lexer'
        def var1 = "let PI_NUMBER = 3.14\n;"
        def lexer = new Lexer(var1)

        when: 'variable gets lexed'
        def letKeyword = lexer.next().get()
        def variableName = lexer.next().get()
        def equalOperator = lexer.next().get()
        def valueNumber = lexer.next().get()
        def semicolonOperator = lexer.next().get()

        then:
        letKeyword == Keyword.LET
        variableName == new Ident("PI_NUMBER")
        equalOperator == Operator.EQUAL
        ((AtomicIdent<Double>)valueNumber).value() == new AtomicIdent<Double>(3.14).value()
        semicolonOperator == Operator.SEMICOLON
    }

}
