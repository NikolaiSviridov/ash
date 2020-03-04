package org.ivm.ash.parser

import java.lang.RuntimeException


/**
 * Маппинги имён переменных на их значения для использования объектами класса Parser.
 */
class VariablesMappings {
    private val variables = mutableMapOf<String, TokenList>()

    companion object {
        val EMPTY_CONTENT = TokenList()
    }

    /**
     * Возвращает Sequence из пар маппингов.
     */
    fun asSequence(): Sequence<Map.Entry<String, TokenList>> {
        return variables.asSequence()
    }

    /**
     * Добавляет новые маппинги, которые конструируются из обычных строк.
     * @param vars Sequence из пар строк
     */
    fun addPlainStrings(vars: Sequence<Map.Entry<String, String>>) {
        for (entry in vars) {
            val tokenList = TokenList()
            tokenList.add(Token(entry.value, TokenType.plain))
            variables[entry.key] = tokenList
        }
    }

    /**
     * Добавляет новый маппинг.
     * @param name Имя перпемнной
     * @param content Список токенов, которые нужно будет подставить вместо переменной
     */
    fun add(name: String, content: TokenList) {
        if (variables.containsKey(name)) {
            println("Warning: Trying to modify existing variable")
        }
        variables[name] = content
    }

    /**
     * Заменяет значение переменной
     * @param name Имя переменной
     * @param content Новый список токенов
     */
    fun replace(name: String, content: TokenList) {
        if (!variables.containsKey(name)) {
            throw RuntimeException()
        }
        variables[name] = content
    }

    /**
     * Возвращает соответствующий имени переменной список токенов.
     * Если переменная с указанным именем не найдена, возвращает пустой список токенов.
     * @param name Имя переменной
     * @return Список токенов, соответствующих переменной
     */
    fun get(name: String): TokenList {
        return variables.getOrDefault(name, null) ?: return EMPTY_CONTENT
    }
}
