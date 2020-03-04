package org.ivm.ash.parser

/**
 * Представляет распаршенную, но не выведенную команду.
 * @param commandName Имя команды
 * @param arguments Список аргументов команды
 */
data class PreCommand(val commandName: String, val arguments: List<String>) {
    var pipedInput = false;
    var pipedStderr = false;
    var pipedStdout = false;

    /**
     * Возвращает конкатенацию аргументов в строку.
     * @return Результат конкатенации списка аргументов в строку
     */
    fun argumentsString(): String {
        return arguments.joinToString("")
    }

    /**
     * Возвращает команду и аргументы, объединённые в список.
     * @return Список, состоящий из команды и её аргументов
     */
    fun toList(): List<String> {
        val list = mutableListOf(commandName)
        list.addAll(arguments)
        return list
    }
}
