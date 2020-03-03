package org.ivm.ash.shell

class Environment {
    val variables = HashMap<String, String>();

    constructor(systemEnvironment: Map<String, String>) {
        variables.putAll(systemEnvironment)
    }

    companion object {
        private const val EMPTY_STRING = ""
    }

    fun asSequence(): Sequence<Map.Entry<String, String>> {
        return variables.asSequence()
    }

    fun getVariable(name: String): String {
        val value = variables.getOrDefault(name, EMPTY_STRING)
        return value
    }

    fun setVariable(name: String, value: String) {
        variables[name] = value
    }
}
