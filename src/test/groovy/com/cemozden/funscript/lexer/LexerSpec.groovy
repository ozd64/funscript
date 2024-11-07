package com.cemozden.funscript.lexer

import com.cemozden.funscript.ast.SyntaxErrorException
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

    def "should parse mathematical statement successfully"() {
        given: 'Math statement definition and lexer'
        def var1 = "x = x + 5;"
        def lexer = new Lexer(var1)

        when: 'variable gets lexed'
        def variableName = lexer.nextToken().get()
        def equalOperator = lexer.nextToken().get()
        def variableName2 = lexer.nextToken().get()
        def plusOperator = lexer.nextToken().get()
        def integerValue = lexer.nextToken().get()
        def semicolonOperator = lexer.nextToken().get()

        then:
        variableName == new Ident("x")
        equalOperator == Operator.EQUAL
        variableName2 == new Ident("x")
        plusOperator == Operator.PLUS
        integerValue == new AtomicIdent(5)
        semicolonOperator == Operator.SEMICOLON
    }

    def "should parse between loop structure successfully"() {
        given: 'between loop and lexer'
        def loopCode = "between i by (1 to 10) {\n" +
                "    print i;\n" +
                "}"
        def lexer = new Lexer(loopCode)

        when:'between loop gets lexed'
        def betweenKeyword = lexer.nextToken().get()
        def variableIdent = lexer.nextToken().get()
        def byKeyword = lexer.nextToken().get()
        def openParentheses = lexer.nextToken().get()
        def integerIdent = lexer.nextToken().get()
        def toKeyword = lexer.nextToken().get()
        def integerIdent2 = lexer.nextToken().get()
        def closedParentheses = lexer.nextToken().get()
        def openCurlyBracket = lexer.nextToken().get()
        def printIdent = lexer.nextToken().get()
        def variableReference = lexer.nextToken().get()
        def semicolonOperator = lexer.nextToken().get()
        def closedBracket = lexer.nextToken().get()

        then:
        betweenKeyword == Keyword.BETWEEN
        variableIdent == new Ident("i")
        byKeyword == Keyword.BY
        openParentheses == Operator.OPEN_PARENTHESES
        integerIdent == new AtomicIdent(1)
        toKeyword == Keyword.TO
        integerIdent2 == new AtomicIdent(10)
        closedParentheses == Operator.CLOSED_PARENTHESES
        openCurlyBracket == Operator.OPEN_CURLY_BRACKET
        printIdent == new Ident("print")
        variableReference == new Ident("i")
        semicolonOperator == Operator.SEMICOLON
        closedBracket == Operator.CLOSED_CURLY_BRACKET
    }

    def "should expect type correctly if the asked type is present"() {
        given: 'a code'
        def loopCode = "i by (1 to 10) {\n" +
                "    print i;\n" +
                "}"
        def lexer = new Lexer(loopCode)

        when: 'expecting valid token'
        def identifier = lexer.expectToken(Ident,
                token -> "Expected identifier got ${token}")

        then: 'tokens should be correctly expected'
        identifier == new Ident("i")
    }

    def "should throw SyntaxErrorException if the expected token is not received"() {
        given: 'a code'
        def loopCode = "between i by (1 to 10) {\n" +
                "    print i;\n" +
                "}"
        def lexer = new Lexer(loopCode)

        when: 'expecting valid token'
        def token = lexer.expectToken(Ident.class, foundToken -> "Expected identifier got ${foundToken}".toString())

        then: 'exception should be thrown'
        def ex = thrown(SyntaxErrorException)

        ex.getMessage() == "Expected identifier got between"
    }

    def "should expect keyword correctly if the asked keyword is present"() {
        given: 'between loop and lexer'
        def loopCode = "between i by (1 to 10) {\n" +
                "    print i;\n" +
                "}"
        def lexer = new Lexer(loopCode)

        when: 'expecting valid token'
        def keyword = lexer.expectKeyword(Keyword.BETWEEN,
                token -> "Expected between got ${token}")

        then: 'tokens should be correctly expected'
        keyword == Keyword.BETWEEN
    }

    def "should throw SyntaxErrorException if the expected keyword is not received"() {
        given: 'between loop and lexer'
        def loopCode = "to i by (1 to 10) {\n" +
                "    print i;\n" +
                "}"
        def lexer = new Lexer(loopCode)

        when: 'expecting valid token'
        def token = lexer.expectKeyword(Keyword.BETWEEN, foundToken -> "Expected between got ${foundToken}".toString())

        then: 'exception should be thrown'
        def ex = thrown(SyntaxErrorException)

        ex.getMessage() == "Expected between got to"
    }

    def "should expect operator correctly if the asked operator is present"() {
        given: 'a code'
        def loopCode = "+"
        def lexer = new Lexer(loopCode)

        when: 'expecting valid token'
        def operator = lexer.expectOperator(Operator.PLUS,
                token -> "Expected + got ${token}")

        then: 'tokens should be correctly expected'
        operator == Operator.PLUS
    }

    def "should throw SyntaxErrorException if the expected operator is not received"() {
        given: 'a code'
        def loopCode = "+"
        def lexer = new Lexer(loopCode)

        when: 'expecting valid token'
        def operator = lexer.expectOperator(Operator.SEMICOLON,
                token -> "Expected ; got ${token}".toString())

        then: 'tokens should be correctly expected'
        def ex = thrown(SyntaxErrorException)

        ex.getMessage() == "Expected ; got +"
    }

}
