package org.ivm.ash.shell

class Environment {
    private val environment = HashMap<String, String>();

    constructor(systemEnvironment: Map<String, String>) {
        environment.putAll(systemEnvironment)
    }

    companion object {
        private const val EMPTY_STRING = ""
    }

    fun asSequence(): Sequence<Map.Entry<String, String>> {
        return environment.asSequence()
    }

    fun getVariable(name: String): String {
        val value = environment.getOrDefault(name, EMPTY_STRING)
        return value
    }

    fun setVariable(name: String, value: String) {
        environment[name] = value
    }
}
