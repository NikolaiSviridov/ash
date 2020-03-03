package org.ivm.ash.parser.unused

class Environment {
    private val variables = mutableMapOf<String, String>()

    fun add(name: String, value: String) {
        if (name in variables) {
            println("Resetting variable value")
        }
        variables[name] = value
    }

    fun get(name: String): String? {
        return variables[name]
    }
}
