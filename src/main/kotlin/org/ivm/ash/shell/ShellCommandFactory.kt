package org.ivm.ash.shell

import org.ivm.ash.parser.PreCommand
import java.lang.IllegalArgumentException

class ShellCommandFactory {
    companion object {
        val changeDirectoryCommandName = "cd"
        val echoCommandName = "echo"
        val exitCommandName = "exit"
        val catCommandName = "cat"

        fun create(preCommand: PreCommand): ShellCommand {
            when (preCommand.commandName) {
                changeDirectoryCommandName -> {
                    if (preCommand.arguments.isEmpty()) {
                        throw IllegalArgumentException("cd command takes exactly one argument!")
                    }
                    return ChangeDirectoryCommand(preCommand.arguments.first())
                }
                echoCommandName -> {
                    TODO("Not implemented")
                }
                exitCommandName -> {
                    TODO("Not implemented")
                }
                catCommandName -> {
                    TODO("Not implemented")
                }
                else -> {
                    throw IllegalStateException()
                }
            }
        }
    }
}
