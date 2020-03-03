package org.ivm.ash.shell

import java.io.File

class EchoCommand(private val arguments: List<String>): ShellCommand {
    private lateinit var output: ByteArray
    private var exitCode = 0

    override fun execute(input: ByteArray?) {
//        output = input ?: arguments.joinToString("").toByteArray()
        val target = arguments.joinToString("") + "\n"
        output = target.toByteArray()
    }

    override fun getOutput(): ByteArray? {
        return output
    }

    override fun getExitCode(): Int {
        return exitCode
    }

    override fun setWorkingDirectory(directory: File) {}

    override fun setEnvironment(environment: Environment) {}
}
