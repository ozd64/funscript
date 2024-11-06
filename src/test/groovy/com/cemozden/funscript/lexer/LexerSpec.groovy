package com.cemozden.funscript.lexer

import spock.lang.Specification

class LexerSpec extends Specification {

    def "should lex an integer variable definition successfully"() {
        given: 'Variable definition and lexer'
        def var1 = "let x=5;"
        def lexer = new Lexer(var1)

        when: 'variable gets lexed'
        def letKeyword = lexer.nextToken().get()
        def variableName = lexer.nextToken().get()
        def equalOperator = lexer.nextToken().get()
        def valueInt = lexer.nextToken().get()
        def semicolonOperator = lexer.nextToken().get()

        then:
        letKeyword == Keyword.LET
        variableName == new Ident("x")
        equalOperator == Operator.EQUAL
        valueInt == new AtomicIdent(5)
        semicolonOperator == Operator.SEMICOLON
    }

    def "should lex a boolean variable definition successfully"() {
        given: 'Variable definition and lexer'
        def var1 = "let is_true = true;"
        def lexer = new Lexer(var1)

        when: 'variable gets lexed'
        def letKeyword = lexer.nextToken().get()
        def variableName = lexer.nextToken().get()
        def equalOperator = lexer.nextToken().get()
        def valueBoolean = lexer.nextToken().get()
        def semicolonOperator = lexer.nextToken().get()

        then:
        letKeyword == Keyword.LET
        variableName == new Ident("is_true")
        equalOperator == Operator.EQUAL
        valueBoolean == new AtomicIdent(true)
        semicolonOperator == Operator.SEMICOLON
    }

    def "should lex an integer variable with explicit type definition successfully"() {
        given: 'Variable definition and lexer'
        def var1 = "let x: Num = 5;"
        def lexer = new Lexer(var1)

        when: 'variable gets lexed'
        def letKeyword = lexer.nextToken().get()
        def variableName = lexer.nextToken().get()
        def colonOperator = lexer.nextToken().get()
        def varType = lexer.nextToken().get()
        def equalOperator = lexer.nextToken().get()
        def valueInt = lexer.nextToken().get()
        def semicolonOperator = lexer.nextToken().get()

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
        def letKeyword = lexer.nextToken().get()
        def variableName = lexer.nextToken().get()
        def equalOperator = lexer.nextToken().get()
        def valueString = lexer.nextToken().get()
        def semicolonOperator = lexer.nextToken().get()

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
        def letKeyword = lexer.nextToken().get()
        def variableName = lexer.nextToken().get()
        def equalOperator = lexer.nextToken().get()
        def valueNumber = lexer.nextToken().get()
        def semicolonOperator = lexer.nextToken().get()

        then:
        letKeyword == Keyword.LET
        variableName == new Ident("PI_NUMBER")
        equalOperator == Operator.EQUAL
        ((AtomicIdent<Double>)valueNumber).value() == new AtomicIdent<Double>(3.14).value()
        semicolonOperator == Operator.SEMICOLON
    }

}
