package org.ivm.ash.shell

import org.ivm.ash.parser.*
import java.io.File
import java.lang.Exception

/**
 * Класс, реализующий функционал шелла.
 * @param arguments Аргументы командной строки, переданные при запуске шелла.
 */
class Shell(private val arguments: ArgumentList) {
    private var environment = Environment(System.getenv())
    private var currentDirectory = File(System.getProperty("user.dir"))
    private val promt = Promt()

    companion object {
        val SUCCESS_EXIT_CODE = 0
    }

    private fun promtLoop() {
        while (true) {
            val input = promt.promtInput(currentDirectory)
            if (input.isEmpty()) {
                continue
            }
            try {
                execute(input)
            }
            catch (exception: Exception) {
                println("Could not parse command")
            }
        }
    }

    /**
     * Запускает цикл ввода пользовательских команд.
     */
    fun start() {
        promtLoop()
    }

    private fun handleFailedCommand(command: Command) {
        if (command.getOutput() != null) {
            System.out.write(command.getOutput())
            System.out.flush()
        }
        println("Command: $command terminated with non-zero exit code: ${command.getExitCode()}")
    }

    private fun execute(input: String) {
        val commandsPipeline = buildCommandPipeline(input)
        var lastOutput: ByteArray? = null
        for (command in commandsPipeline) {
            command.setWorkingDirectory(currentDirectory)
            command.setEnvironment(environment)
            command.execute(lastOutput)
            if (command.getExitCode() != SUCCESS_EXIT_CODE) {
                handleFailedCommand(command)
                return
            }
            if (command is InternalShellCommand) {
                currentDirectory = command.getModifiedWorkingDirectory()
                environment = command.getModifiedEnvironment()
            }
            lastOutput = command.getOutput()
        }
        if (lastOutput != null) {
            System.out.write(lastOutput)
            System.out.flush()
        }
    }

    private fun constructParserEnvironment(): VariablesMappings {
        val mappings = VariablesMappings()
        mappings.addPlainStrings(environment.asSequence())
        return mappings
    }

    private fun updateEnvironment(mappings: VariablesMappings) {
        for (mapping in mappings.asSequence()) {
            environment.setVariable(mapping.key, mapping.value.joinToString { it.value })
        }
    }

    private fun buildCommandPipeline(input: String): List<Command> {
        val variablesMappings = constructParserEnvironment()
        val parser = Parser(input, variablesMappings)
        val preCommandsBuilder = PreCommandsBuilder(parser.parse())
        preCommandsBuilder.build()
        val commandsBuilder = CommandsBuilder(preCommandsBuilder.commands)
        commandsBuilder.build()
        updateEnvironment(parser.mappings)
        return commandsBuilder.commands
    }
}
