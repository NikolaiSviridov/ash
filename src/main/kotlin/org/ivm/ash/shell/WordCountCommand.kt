package org.ivm.ash.shell

import java.io.*
import java.nio.file.Files
import java.util.*


/**
 * Реализация команды wc
 */
class WordCountCommand(private val arguments: List<String>): ShellCommand {
    private var output: ByteArray? = null
    private var exitCode = 0
    private lateinit var workingDirectory: File

    override fun execute(input: ByteArray?) {
        val result = ByteArrayOutputStream()
        if (input != null) {
            val bytes = input.size.toLong()
            val words = getWords(ByteArrayInputStream(input))
            val lines = getLines(input)
            putResult(lines, words, bytes, "", result)
            output = result.toByteArray()
            return
        }
        for (file in arguments) {
            val target = workingDirectory.resolve(file)
            if (!target.exists()) {
                exitCode = 1
                break
            }
            try {
                val lines = getLines(target)
                val words = getWords(FileInputStream(target))
                val bytes = getBytes(target)
                putResult(lines, words, bytes, file, result)
            }
            catch (exception: IOException) {
                exitCode = 1
                return
            }
        }
        output = result.toByteArray()
    }

    private fun getLines(input: ByteArray): Long {
        val reader = Scanner(ByteArrayInputStream(input))
        var count: Long = 0
        while (reader.hasNextLine()) {
            reader.nextLine()
            count += 1
        }
        return count
    }

    private fun putResult(lines: Long, words: Long, bytes: Long, file: String, result: ByteArrayOutputStream) {
        result.write(lines.toString().toByteArray())
        result.write("    ".toByteArray())
        result.write(words.toString().toByteArray())
        result.write("    ".toByteArray())
        result.write(bytes.toString().toByteArray())
        result.write("    ".toByteArray())
        result.write(file.toByteArray())
        result.write("\n".toByteArray())
    }

    private fun getBytes(file: File): Long {
        return file.length()
    }

    private fun getWords(stream: InputStream): Long {
        val scanner = Scanner(stream)
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