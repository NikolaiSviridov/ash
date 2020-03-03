package org.ivm.ash.shell

import java.io.File
import kotlin.system.exitProcess

class ExitCommand: ShellCommand {
    override fun execute(input: ByteArray?) {
        exitProcess(0)
    }

    override fun getOutput(): ByteArray? {
        return null
    }

    override fun getExitCode(): Int {
        return 0
    }

    override fun setWorkingDirectory(directory: File) {}

    override fun setEnvironment(environment: Environment) {}
}