package org.ivm.ash.parser

/**
 * Билдер, для формирования PreCommand. Фильтрует блоки токенов и конструирует из них PreCommand.
 * @param blocks Список блоков токенов
 */
class PreCommandsBuilder(val blocks: List<TokenList>) {
    /**
     * Список сформированных PreCommand
     */
    val commands = mutableListOf<PreCommand>()

    /**
     * Строит список PreCommand
     */
    fun build() {
        for (block in blocks) {
            val block = block.dropWhile { it.type == TokenType.space }
            val name = block.first().value
            if (name.isEmpty()) {
                continue
            }
            val arguments = block.drop(1).filter { it.type != TokenType.space }.map { it.value }
            val command = PreCommand(name, arguments)
            commands.add(command)
            command.pipedInput = true
//            command.pipedStderr = true
            command.pipedStdout = true
        }
        if (commands.isEmpty()) {
            return
        }
        commands.first().pipedInput = false
        commands.last().pipedStdout = false
    }
}
