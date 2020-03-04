package org.ivm.ash.parser

/**
 * Строковые разделители, используемые парсером
 */
class Delimiter {
    class Value {
        companion object {
            val SPACE = " "
            val DOLLAR = "$"
            val EQUALS = "="
            //        val SEMICOLON = ";"
            val SQUOTE = "'"
            val DQUOTE = "\""
            val PIPE = "|"
        }
    }
}
