package org.ivm.ash.shell

import java.io.ByteArrayOutputStream
import java.io.File


class LsCommand(private val arguments: List<String>) : ShellCommand {
    private var output: ByteArray? = null
    private var exitCode = 0
    private lateinit var workingDirectory: File

    override fun execute(input: ByteArray?) {
        val result = ByteArrayOutputStream()


        if (arguments.isEmpty() || input != null) {
            workingDirectory.walk(FileWalkDirection.BOTTOM_UP).maxDepth(1).forEach {
                val out = it.name + "\n"
                result.write(out.toByteArray())
            }
            output = result.toByteArray()
            return
        }

        var printPath = false
        if (arguments.size > 1) {
            printPath = true
        }

        for (filePath in arguments) {
            val file = workingDirectory.resolve(File(filePath))
            if (file.isDirectory) {
                if (printPath) {
                    val msg = file.absolutePath + ":\n"
                    result.write(msg.toByteArray())
                }
                file.walk().forEach {
                    val out = it.name + "\n"
                    result.write(out.toByteArray())
                }
            } else if (file.isFile){
                result.write(file.name.toByteArray())
            } else {
                val msg = "ls: cannot access '" + file.name + "': No such file or directory\n"
                result.write(msg.toByteArray())
            }
        }

        output = result.toByteArray()
    }

    override fun toString(): String {
        return "ls " + arguments.joinToString(" ")
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