package org.ivm.ash.parser

class TokenSplitter(private val input: String) {
    val tokens = arrayListOf<Token>()

    companion object {
        // TODO: Contruct from Delimiters
        private val regex = Regex("(?<=[ $='\"|])|(?=[ $='\"|])")
    }

    fun build(): ArrayList<Token> {
        val split = input.split(regex)
        var position = 0
        while (position < split.size) {
            position = when (split[position]) {
                Delimiter.Value.DOLLAR -> {
                    if (position + 1 >= split.size) {
                        throw InvalidSyntaxException()
                    }
                    val name = split[position + 1]
                    tokens.add(Token(name, TokenType.evariable))
                    position + 2
                }
                Delimiter.Value.SQUOTE -> {
                    tokens.add(Token(Delimiter.Value.SQUOTE, TokenType.squote))
                    position + 1
                }
                Delimiter.Value.DQUOTE -> {
                    tokens.add(Token(Delimiter.Value.DQUOTE, TokenType.dquote))
                    position + 1
                }
                Delimiter.Value.PIPE -> {
                    tokens.add(Token(Delimiter.Value.PIPE, TokenType.pipe))
                    position + 1
                }
                Delimiter.Value.SPACE -> {
                    tokens.add(Token(Delimiter.Value.SPACE, TokenType.space))
                    position + 1
                }
                else -> {
                    if (position + 1 < split.size && split[position + 1] == Delimiter.Value.EQUALS) {
                        tokens.add(Token(split[position], TokenType.avariable))
                        position + 2
                    }
                    else {
                        tokens.add(Token(split[position], TokenType.other))
                        position + 1
                    }
                }
            }
        }
        val result = tokens.filter { it.value.isNotEmpty() }
        return ArrayList(result)
    }
}
