package org.ivm.ash.shell

import java.io.File

class ChangeDirectoryCommand(private val argument: String): InternalShellCommand {
    private var exitCode = 0
    private lateinit var currentDirectory: File
    private lateinit var environment: Environment

    override fun setEnvironment(environment: Environment) {
        this.environment = environment
    }

    override fun setWorkingDirectory(currentDirectory: File) {
        this.currentDirectory = currentDirectory
    }

    override fun getModifiedEnvironment(): Environment {
        return environment
    }

    override fun getModifiedWorkingDirectory(): File {
        return currentDirectory
    }

    override fun toString(): String {
        return "cd $argument"
    }

    override fun execute(input: ByteArray?) {
        val target = currentDirectory.resolve(File(argument)).absoluteFile
        if (!target.exists()) {
            exitCode = 1
            return
        }
        currentDirectory = target
    }

    override fun getOutput(): ByteArray? {
        return null
    }

    override fun getExitCode(): Int {
        return exitCode
    }
}
