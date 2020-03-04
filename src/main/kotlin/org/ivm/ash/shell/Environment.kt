package org.ivm.ash.shell


/**
 * Окружение, в котором выполняются команды.
 * Представляет из себя набор мапингов между именами переменных окружения и их значениями.
 */
class Environment {
    val variables = HashMap<String, String>();

    /**
     * @param systemEnvironment Начальное окружение. (Окружение, в котором запускается шелл)
     */
    constructor(systemEnvironment: Map<String, String>) {
        variables.putAll(systemEnvironment)
    }

    companion object {
        private const val EMPTY_STRING = ""
    }

    /**
     * Возвращает Sequence из пар имён переменных и их значений.
     * @return Sequence из пар имён и их значений
     */
    fun asSequence(): Sequence<Map.Entry<String, String>> {
        return variables.asSequence()
    }

    /**
     * Возвращает значение переменной.
     * @param name Имя переменной
     * @return Значение, соответствующее переменной или пустую строку
     */
    fun getVariable(name: String): String {
        val value = variables.getOrDefault(name, EMPTY_STRING)
        return value
    }

    /**
     * Устанавливает значение переменной.
     * Если переменная уже была установлена, то обновляет её значение.
     * @param name Имя переменной
     * @param value Значение переменной
     */
    fun setVariable(name: String, value: String) {
        variables[name] = value
    }
}
