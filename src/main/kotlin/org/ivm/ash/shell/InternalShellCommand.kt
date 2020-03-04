package org.ivm.ash.shell

import java.io.File

/**
 * Расширяет ShellCommand возможностью модифицировать состояние шелла.
 */
interface InternalShellCommand: ShellCommand {
    /**
     * Возвращает модифицированное командой окружение.
     * @return Модифицированное окружение
     */
    fun getModifiedEnvironment(): Environment

    /**
     * Возвращает модифицированную командой текущую рабочую директорию.
     * @return Новая рабочая директория
     */
    fun getModifiedWorkingDirectory(): File
}
