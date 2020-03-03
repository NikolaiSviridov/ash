package org.ivm.ash.shell

import org.ivm.ash.parser.CommandsBuilder
import org.ivm.ash.parser.Parser
import org.ivm.ash.parser.PreCommand
import org.ivm.ash.parser.VariablesMappings
import java.io.File


class Shell(private val arguments: ArgumentList) {
    private val environment = Environment(System.getenv())
    private val currentDirectory = File(System.getProperty("user.dir"))
    private val promt = Promt()

    companion object {
        private val shellCommands = listOf("exit", "ls", "cat", "echo", "cd", "clear")
    }

    private fun startProcess(command: PreCommand, input: String?): Process {
        val target = command.commandName + command.arguments.joinToString(" ")
        val builder = ProcessBuilder(target);
        builder.directory(currentDirectory)
        val environment = builder.environment()
        environment.putAll(environment)
        builder.redirectOutput(ProcessBuilder.Redirect.PIPE)
//        builder.redirectInput(ProcessBuilder.Redirect.PIPE)
        if (input == null) {

        }
        else {

        }
        builder.redirectError(ProcessBuilder.Redirect.PIPE)
        return builder.start()
    }

    fun execute(commands: Iterable<PreCommand>) {
        var lastResult = ""
        for (preCommand in commands) {
            if (preCommand.commandName in shellCommands) {
//                val result = runCommand()
            }
            else {
            }
        }
    }

    fun promtLoop() {
        while (true) {
            val input = promt.promtInput(currentDirectory)
            val result = execute(input)
            if (result != 0) {
                println("Terminated with non-zero exit code: $result")
            }
        }
    }

    fun start() {
        promtLoop()
    }

    private fun execute(input: String): Int {
        val commandsPipeline = buildCommandPipeline(input)
        var lastOutput: ByteArray? = null
        for (command in commandsPipeline) {
            val commandList = mutableListOf(command.commandName)
            commandList.addAll(command.arguments)
            val currentOutput = runProcess(commandList, lastOutput)
            lastOutput = currentOutput
        }
        if (lastOutput != null) {
            System.out.write(lastOutput)
            System.out.flush()
        }
        return 0
    }

    private fun runProcess(command: List<String>, input: ByteArray? = null): ByteArray {
        val builder = ProcessBuilder(command);
        builder.directory(File(System.getProperty("user.dir")))
        if (input != null) {
            builder.redirectOutput(ProcessBuilder.Redirect.INHERIT)
        }
        else {
            builder.redirectOutput(ProcessBuilder.Redirect.PIPE)
        }
        builder.redirectError(ProcessBuilder.Redirect.INHERIT)
        val process = builder.start()
        if (input != null) {
            process.outputStream.write(input)
            // Simply it's equivalent to sending Ctrl-D
            process.outputStream.close()
        }
        val exitCode = process.waitFor()
//        println("exit code: $exitCode")
        return process.inputStream.readAllBytes()
    }

    private fun constructParserEnvironment(): VariablesMappings {
        val mappings = VariablesMappings()
        mappings.addPlainStrings(environment.asSequence())
        return mappings
    }

    private fun updateEnvironment(mappings: VariablesMappings) {
        for (mapping in mappings.asSequence()) {
            environment.setVariable(mapping.key, mapping.value.joinToString(""))
        }
    }

    private fun buildCommandPipeline(input: String): List<PreCommand> {
        val variablesMappings = constructParserEnvironment()
        val parser = Parser(input, variablesMappings)
        val commandsBuilder = CommandsBuilder(parser.parse())
        updateEnvironment(parser.mappings)
        commandsBuilder.build()
        return commandsBuilder.commands
    }
}
