package org.ivm.ash.shell

import java.io.File

interface Command {
    fun execute(input: ByteArray? = null)
    fun getOutput(): ByteArray?
    fun getExitCode(): Int
    fun setWorkingDirectory(directory: File)
    fun setEnvironment(environment: Environment)
}
