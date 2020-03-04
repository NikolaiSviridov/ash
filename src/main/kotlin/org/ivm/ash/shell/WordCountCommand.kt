package org.ivm.ash.shell

import java.io.*
import java.nio.file.Files
import java.util.*

class WordCountCommand(private val arguments: List<String>): ShellCommand {
    private var output: ByteArray? = null
    private var exitCode = 0
    private lateinit var workingDirectory: File

    override fun execute(input: ByteArray?) {
        val result = ByteArrayOutputStream()
        for (file in arguments) {
            val target = workingDirectory.resolve(file)
            if (!target.exists()) {
                exitCode = 1
                break
            }
            try {
                result.write(getLines(target).toString().toByteArray())
                result.write("    ".toByteArray())
                result.write(getWords(target).toString().toByteArray())
                result.write("    ".toByteArray())
                result.write(getBytes(target).toString().toByteArray())
                result.write("    ".toByteArray())
                result.write(file.toByteArray())
                result.write("\n".toByteArray())
            }
            catch (exception: IOException) {
                exitCode = 1
                return
            }
        }
        output = result.toByteArray()
    }

    private fun getBytes(file: File): Long {
        return file.length()
    }

    private fun getWords(file: File): Long {
        val scanner = Scanner(FileInputStream(file))
        var count: Long = 0
        while (scanner.hasNext()) {
            scanner.next()
            count += 1
        }
        return count
    }

    override fun toString(): String {
        return "wc " + arguments.joinToString(" ")
    }

    private fun getLines(file: File): Long {
        return Files.lines(file.toPath()).count()
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

    override fun setEnvironment(environment: Environment) {}
}