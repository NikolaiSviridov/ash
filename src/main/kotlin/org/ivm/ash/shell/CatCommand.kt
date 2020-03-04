package org.ivm.ash.shell

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException


/**
 * Реализация команды cat.
 * @param arguments Список аргументов - имена файлов, содержимое которых нужно вывести (может быть пустым)
 */
class CatCommand(private val arguments: List<String>): ShellCommand {
    private var output: ByteArray? = null
    private var exitCode = 0
    private lateinit var workingDirectory: File

    override fun execute(input: ByteArray?) {
        if (input != null) {
            output = input
            return
        }
        val result = ByteArrayOutputStream()
        for (filePath in arguments) {
            val file = workingDirectory.resolve(File(filePath))
            try {
                val reader = FileInputStream(file)
                result.write(reader.readAllBytes())
                result.write("\n".toByteArray())
            }
            catch (exception: IOException) {
                exitCode = 1
                break
            }
        }
        output = result.toByteArray()
    }

    override fun toString(): String {
        return "cat " + arguments.joinToString(" ")
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
