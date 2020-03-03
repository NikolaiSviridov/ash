package org.ivm.ash.parser

data class PreCommand(val commandName: String, val arguments: List<String>) {
    var pipedInput = false;
    var pipedStderr = false;
    var pipedStdout = false;

    fun argumentsString(): String {
        return arguments.joinToString("")
    }

    fun toList(): List<String> {
        val list = mutableListOf(commandName)
        list.addAll(arguments)
        return list
    }
}
