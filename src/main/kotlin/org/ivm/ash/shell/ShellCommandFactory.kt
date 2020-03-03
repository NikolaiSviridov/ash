package org.ivm.ash.shell

import org.ivm.ash.parser.PreCommand
import java.lang.IllegalArgumentException

class ShellCommandFactory {
    companion object {
        const val changeDirectoryCommandName = "cd"
        const val echoCommandName = "echo"
        const val exitCommandName = "exit"
        const val catCommandName = "cat"
        const val pwdCommandName = "pwd"

        fun create(preCommand: PreCommand): ShellCommand {
            when (preCommand.commandName) {
                changeDirectoryCommandName -> {
                    if (preCommand.arguments.isEmpty()) {
                        throw IllegalArgumentException("cd command takes exactly one argument!")
                    }
                    return ChangeDirectoryCommand(preCommand.arguments.first())
                }
                echoCommandName -> {
                    return EchoCommand(preCommand.arguments)
                }
                exitCommandName -> {
                    return ExitCommand()
                }
                catCommandName -> {
                    return CatCommand(preCommand.arguments)
                }
                pwdCommandName -> {
                    return ProcessWorkingDirectoryCommand()
                }
                else -> {
                    throw IllegalStateException()
                }
            }
        }
    }
}
