package org.ivm.ash.shell

import java.io.File

class ExternalCommand(private val command: List<String>): Command {
    private var output = ByteArray(0)
//    private var error = ByteArray(0)
    private var exitCode = 0

    override fun execute(input: ByteArray?) {
        runProcess(input)
    }

    override fun getOutput(): ByteArray? {
        return output
    }

    override fun getExitCode(): Int {
        return exitCode
    }

    override fun toString(): String {
        return command.first() + " " + command.drop(1).joinToString("")
    }

    private fun runProcess(input: ByteArray? = null) {
        val builder = ProcessBuilder(command);
        builder.directory(File(System.getProperty("user.dir")))
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
            // Simply it's equivalent to sending Ctrl-D
            process.outputStream.close()
        }
        exitCode = process.waitFor()
        output = process.inputStream.readAllBytes()
    }
}
