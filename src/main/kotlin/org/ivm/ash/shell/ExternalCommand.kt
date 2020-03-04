package org.ivm.ash.shell

import java.io.File

class ExternalCommand(private val command: List<String>): Command {
    private var output: ByteArray? = null
    private var exitCode = 0
    private lateinit var workingDirectory: File
    private lateinit var environment: Environment

    override fun execute(input: ByteArray?) {
        val name = command.first()
        if (!workingDirectory.resolve(name).exists()) {
            exitCode = 1
            return
        }
        runProcess(input)
    }

    override fun getOutput(): ByteArray? {
        return output
    }

    override fun getExitCode(): Int {
        return exitCode
    }

    override fun setWorkingDirectory(directory: File) {
        workingDirectory = directory
    }

    override fun setEnvironment(environment: Environment) {
        this.environment = environment
    }

    override fun toString(): String {
        return command.first() + " " + command.drop(1).joinToString("")
    }

    private fun runProcess(input: ByteArray? = null) {
        val builder = ProcessBuilder(command);
        builder.directory(workingDirectory)
        builder.environment().putAll(environment.variables)
        if (input != null) {
            builder.redirectOutput(ProcessBuilder.Redirect.INHERIT)
        }
        else {
            builder.redirectOutput(ProcessBuilder.Redirect.PIPE)
        }
        builder.redirectError(ProcessBuilder.Redirect.INHERIT)
        val process = builder.start()
        if (input != null) {
            process.outputStream.write(input)
            // It's equivalent to sending Ctrl-D
            process.outputStream.close()
        }
        exitCode = process.waitFor()
        output = process.inputStream.readAllBytes()
    }
}
