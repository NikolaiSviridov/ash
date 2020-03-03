package org.ivm.ash.parser

import java.lang.RuntimeException

class VariablesMappings {
    private val variables = mutableMapOf<String, TokenList>()

    companion object {
        val EMPTY_CONTENT = TokenList()
    }

    fun asSequence(): Sequence<Map.Entry<String, TokenList>> {
        return variables.asSequence()
    }

    fun addPlainStrings(vars: Sequence<Map.Entry<String, String>>) {
        for (entry in vars) {
            val tokenList = TokenList()
            tokenList.add(Token(entry.value, TokenType.plain))
            variables[entry.key] = tokenList
        }
    }

    fun add(name: String, content: TokenList) {
        if (variables.containsKey(name)) {
            println("Warning: Trying to modify existing variable")
        }
        variables[name] = content
    }

    fun replace(name: String, content: TokenList) {
        if (!variables.containsKey(name)) {
            throw RuntimeException()
        }
        variables[name] = content
    }

    fun get(name: String): TokenList {
        return variables.getOrDefault(name, null) ?: return EMPTY_CONTENT
    }
}
