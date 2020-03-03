package org.ivm.ash.shell

interface Command {
    fun execute(input: ByteArray? = null)
    fun getOutput(): ByteArray?
    fun getExitCode(): Int
}
