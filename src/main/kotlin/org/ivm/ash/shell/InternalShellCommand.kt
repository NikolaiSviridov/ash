package org.ivm.ash.shell

import java.io.File

interface InternalShellCommand: ShellCommand {
    fun setShellEnvironment(environment: Environment)
    fun setShellCurrentDirectory(currentDirectory: File)
    fun getShellEnvironment(): Environment
    fun getShellCurrentDirectory(): File
}
