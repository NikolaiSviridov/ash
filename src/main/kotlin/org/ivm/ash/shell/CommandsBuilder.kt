package org.ivm.ash.shell

import org.ivm.ash.parser.PreCommand

/**
 * Строит полноценные команды из PreCommand.
 * @param preCommands Список PreCommand
 */
class CommandsBuilder(private val preCommands: List<PreCommand>) {
    /**
     * Список сформированных команд.
     */
    val commands = mutableListOf<Command>()

    private fun createCommand(preCommand: PreCommand): Command {
        if (preCommand.commandName in ShellCommandFactory.COMMANDS) {
            return ShellCommandFactory.create(preCommand)
        }
        return ExternalCommand(preCommand.toList())
    }

    /**
     * Строит список команд.
     */
    fun build() {
        for (preCommand in preCommands) {
            commands.add(createCommand(preCommand))
        }
    }
}
