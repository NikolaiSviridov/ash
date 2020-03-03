package org.ivm.ash.shell

import org.ivm.ash.parser.PreCommand

class CommandsBuilder(private val preCommands: List<PreCommand>) {
    val commands = mutableListOf<Command>()

    private fun createCommand(preCommand: PreCommand): Command {
        if (preCommand.commandName in Shell.internalCommands) {
            return ShellCommandFactory.create(preCommand)
        }
        return ExternalCommand(preCommand.toList())
    }

    fun build() {
        for (preCommand in preCommands) {
            commands.add(createCommand(preCommand))
        }
    }
}
