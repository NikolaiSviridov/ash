package org.ivm.ash.parser

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*

internal class ParserTest {
    @Test
    fun basicSubstitutionQuotedVariable() {
        val input = "some='1111' '\$some' \$some"
        val parser = Parser(input)
        val result = Parser.joinTokensToString(parser.parse().first())
        assertEquals(" \$some 1111", result)
    }

    @Test
    fun basicSubstitutionEmptyMapping() {
        val input = "some='1111' \$more"
        val parser = Parser(input)
        val result = Parser.joinTokensToString(parser.parse().first())
        assertEquals(" ", result)
    }
}
