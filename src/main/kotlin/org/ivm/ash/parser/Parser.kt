package org.ivm.ash.parser

class Parser(private val input: String, val mappings: VariablesMappings = VariablesMappings()) {
    private val tokens = TokenSplitter(input).build()
    private var position = 0
    private var currentToken = tokens[position]

    private fun evaluateVariable(): Int {
        val substitution = mappings.get(currentToken.value)
        tokens.addAll(position + 1, substitution)
        tokens.remove(currentToken)
//        substitutionHappened = true
        return position
    }

    private fun demoteSingleQuotedVariables(range: IntRange) {
        for (it in range) {
            val token = tokens[it]
            if (token.type != TokenType.evariable) {
                continue
            }
            token.value = "$" + token.value
            token.type = TokenType.other
        }
    }

    private fun handleSingleQuotes(): Int {
        // TODO: Drop single quotes then adding to substitution map
        // Leave quotes content unchanged
        val rest = tokens.drop(position + 1)
        var rightIndex = rest.indexOfFirst { it.type == TokenType.squote }
        val tokensCount = rightIndex - 1
        rightIndex += position + 1
        // rightIndex is now the index of closing quote
        // Remove closing first
        tokens.removeAt(rightIndex)
        tokens.removeAt(position)
        val next = position + tokensCount + 1
        demoteSingleQuotedVariables(position until next)
        // position will now be the index of first token from substitution
        return next
    }

    private fun handleVariableAssignment(): Int {
        if (position + 1 >= tokens.size) {
            // TODO: Change exception or assign empty string
            throw RuntimeException()
        }
//        substitutionHappened = true
        val nextToken = tokens[position + 1]
        when (nextToken.type) {
            TokenType.squote, TokenType.dquote -> {
                // TODO: Test position
                // Drop all tokens up to nextToken
                val dropCount = position + 2
                val rest = tokens.drop(dropCount)
                var rightIndex = rest.indexOfFirst { it.type == nextToken.type }
                rightIndex += dropCount + 1
                // TODO: Check indices
                // TODO: Refactor list stuff
                val content = TokenList()
                content.addAll(tokens.slice(position + 1 until rightIndex))
                mappings.add(currentToken.value, content)
                tokens.remove(currentToken)
                for (token in content) {
                    tokens.remove(token)
                }
                return position
            }
        }
        // TODO: Refactor list stuff
        val content = TokenList()
        content.add(nextToken)
        mappings.add(currentToken.value, content)
        tokens.remove(currentToken)
        return position + 1
    }

    private fun substitute(): Boolean {
//        var position = 0
        var substitutionHappened = false
        while (position < tokens.size) {
            currentToken = tokens[position]
            position = when (currentToken.type) {
                TokenType.evariable -> evaluateVariable()
                TokenType.squote -> handleSingleQuotes()
                TokenType.avariable -> handleVariableAssignment()
                else -> position + 1
            }
        }
        return substitutionHappened
    }

    companion object {
        fun joinTokensToString(tokens: TokenList): String {
            val result = arrayListOf<String>()
            for (token in tokens) {
                when (token.type) {
                    TokenType.evariable -> {
                        result.add(token.value)
                        result.add(Delimiter.Value.EQUALS)
                    }
                    else -> result.add(token.value)
                }
            }
            return result.joinToString("")
        }
    }

    fun parse(): List<TokenList> {
        substitute()
        return splitByPipes()
    }

    private fun splitByPipes(): List<TokenList> {
        val result = mutableListOf<TokenList>()
        var currentBlock = TokenList()
        for (token in tokens) {
            when (token.type) {
                TokenType.pipe -> {
                    result.add(currentBlock)
                    currentBlock = TokenList()
                }
                else -> {
                    currentBlock.add(token)
                }
            }
        }
        if (currentBlock.isNotEmpty()) {
            result.add(currentBlock)
        }
        return result
    }
}
