package org.ivm.ash.shell

import java.io.File

class ProcessWorkingDirectoryCommand: ShellCommand {
    private lateinit var currentDirectory: File

    override fun execute(input: ByteArray?) {}

    override fun getOutput(): ByteArray? {
        return currentDirectory.absolutePath.toByteArray()
    }

    override fun getExitCode(): Int {
        return 1
    }

    override fun setWorkingDirectory(directory: File) {
        currentDirectory = directory
    }

    override fun setEnvironment(environment: Environment) {}
}
