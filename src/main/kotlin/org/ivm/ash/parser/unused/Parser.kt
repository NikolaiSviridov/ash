package org.ivm.ash.parser.unused

class Parser(private val input: String, private val environment: Environment) {
    private fun findVariableDelimiter(input: String): Int {
        val result = input.indexOfFirst {
            when (it) {
                '$', ';', '"', '\'', ' ', '=' -> true
                else -> false
            }
        }
        if (result == -1) {
            return input.indices.last
        }
        return result
    }

    fun all() {
        val nodeSplitter = NodeSplitter(input)
        nodeSplitter.split()
        val expanded = nodeSplitter.nodes.map {
            it.map { block ->
                when (block.type) {
                    BlockType.unquoted ->
                        Block(
                            expandVariables(block.value),
                            BlockType.unquoted
                        )
                    BlockType.dquoted ->
                        Block(
                            expandVariables(block.value),
                            BlockType.squoted
                        )
                    else -> block
                }
            }
        }
    }

    fun stuff() {

    }

    fun expandVariables(block: String): String {
        return block.replace(Regex("\\\$[a-zA-Z0-9]+")) {
            val name = it.value.drop(1)
            //  TODO: Handle null value
            environment.get(name) ?: ""
        }
    }
}
