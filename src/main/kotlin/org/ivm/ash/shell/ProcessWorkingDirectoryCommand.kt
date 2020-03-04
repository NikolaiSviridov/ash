package org.ivm.ash.shell

import java.io.File

/**
 * Реализация комадны pwd
 */
class ProcessWorkingDirectoryCommand: ShellCommand {
    private lateinit var currentDirectory: File

    override fun execute(input: ByteArray?) {}

    override fun getOutput(): ByteArray? {
        return (currentDirectory.absolutePath + "\n").toByteArray()
    }

    override fun getExitCode(): Int {
        return 0
    }

    override fun setWorkingDirectory(directory: File) {
        currentDirectory = directory
    }

    override fun setEnvironment(environment: Environment) {}
}
