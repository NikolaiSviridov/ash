package org.ivm.ash.shell

import java.io.File

interface InternalShellCommand: ShellCommand {
    fun getModifiedEnvironment(): Environment
    fun getModifiedWorkingDirectory(): File
}
