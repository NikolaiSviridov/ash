package org.ivm.ash.parser

/**
 * Базовый класс токена.
 * @param value Строковое значение токена
 * @param type Тип токена
 */
data class Token(var value: String, var type: TokenType) {}

typealias TokenList = ArrayList<Token>
