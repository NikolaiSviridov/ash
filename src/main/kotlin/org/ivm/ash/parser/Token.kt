package org.ivm.ash.parser

data class Token(var value: String, var type: TokenType) {}

typealias TokenList = ArrayList<Token>
