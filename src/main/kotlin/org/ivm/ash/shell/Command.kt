package org.ivm.ash.shell

import java.io.File

/**
 * Базовый интерфейс для команд.
 */
interface Command {
    /**
     * Выполняет команду.
     * @param input Данные, которые нужно подать на вход команде. Если null, то команда использует stdin (если ей нужно)
     */
    fun execute(input: ByteArray? = null)

    /**
     * Возвращает stdout команды.
     * @return Буфер, в котором содержится вывод команды
     */
    fun getOutput(): ByteArray?

    /**
     * Возвращает код ошибки, с которым завершилась команда.
     * @return Код ошибки, с которым завершилось выполнение команды
     */
    fun getExitCode(): Int

    /**
     * Устанавливает рабочую директорию  команды.
     * @param directory Рабочая директория, в которой будет выполняться команда
     */
    fun setWorkingDirectory(directory: File)

    /**
     * Устанавливает окружение, в котором будет выполняться команда.
     * @param environment Окружение
     */
    fun setEnvironment(environment: Environment)
}
