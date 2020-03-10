package org.ivm.ash.shell

import java.io.*
import java.util.*

class GrepCommand(private val argumentList: ArgumentList): ShellCommand {
    private val arguments = GrepCommandArgumentsParser(argumentList)
    private var exitCode: Int = 0
    private val outputStream = ByteArrayOutputStream()
    private lateinit var workingDirectory: File

    override fun execute(input: ByteArray?) {
        var pattern = arguments.pattern
        if (arguments.searchWords) {
            pattern = ".*\\b$pattern\\b.*"
        }
        val regex = when (arguments.ignoreCase) {
            true -> Regex(pattern, RegexOption.IGNORE_CASE)
            else -> Regex(pattern)
        }
        val reader = when (input != null) {
            true -> Scanner(ByteArrayInputStream(input))
            else -> {
                val file = File(arguments.inputFile)
                if (!file.exists()) {
                    outputStream.writeBytes("File not found!".toByteArray())
                    exitCode = 1
                    return
                }
                Scanner(FileReader(File(arguments.inputFile)))
            }
        }
        var matchesCount = 0
        val stream = Scanner(FileReader(File(arguments.inputFile)))
        var linesNeeded = 0
        while (stream.hasNextLine()) {
            val line = stream.nextLine()
            if (regex.find(line) == null) {
                if (linesNeeded != 0) {
                    outputStream.write((line + "\n").toByteArray())
                    linesNeeded -= 1
                }
                continue
            }
            matchesCount += 1
            outputStream.write((line + "\n").toByteArray())
            linesNeeded = arguments.printLinesAfter
        }
        if (matchesCount == 0) {
            exitCode = 1
        }
    }

    override fun toString(): String {
        return "grep " + argumentList.joinToString("")
    }

    override fun getOutput(): ByteArray? {
        return outputStream.toByteArray()
    }

    override fun getExitCode(): Int {
        return exitCode
    }

    override fun setWorkingDirectory(directory: File) {
        workingDirectory = directory
    }

    override fun setEnvironment(environment: Environment) {}
}
