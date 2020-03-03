package org.ivm.ash.parser.unused

class NodeSplitter(private val input: String) {
    val nodes = arrayListOf<ArrayList<Block>>()

    private var blocks = arrayListOf<Block>()
    private var blockLeftIndex = 0
    private val range = input.indices

    private fun determineQuotedBlockType(char: Char): BlockType {
        return when (char) {
            '\'' -> BlockType.squoted
            else -> BlockType.dquoted
        }
    }

    private fun buildQuotedBlock(subRange: IntRange): Block? {
        val substring = input.substring(subRange).trim()
        if (substring.isEmpty()) {
            return null
        }
        return Block(substring, determineQuotedBlockType(substring.first()))
    }

    private fun buildUnquotedBlock(subRange: IntRange): Block? {
        val substring = input.substring(subRange).trim()
        if (substring.isEmpty()) {
            return null
        }
        return Block(substring, BlockType.unquoted)
    }

    private fun addBlock(block: Block?) {
        if (block == null) {
            return
        }
        blocks.add(block)
    }

    private fun findNextQuote(range: IntRange, quote: Char): Int {
        return range.first {
            input[it] == quote
        }
    }

    private fun commitBlocks() {
        nodes.add(blocks)
        blocks = arrayListOf<Block>()
    }

    private fun commitLast() {
        val subRange = blockLeftIndex..range.last
        if (subRange.count() > 0) {
            addBlock(buildUnquotedBlock(subRange))
        }
        if (blocks.isNotEmpty()) {
            commitBlocks()
        }
    }

    private val delimiters = arrayOf(' ', ';', '$', '=', '\'', '"', '|')

    fun split() {
        var position = range.first
        while (position <= range.last) {
            position = when (input[position]) {
                '\'', '"' -> {
                    addBlock(buildUnquotedBlock(blockLeftIndex until position))
                    val closingQuotePos = findNextQuote(position + 1 until range.last, input[position])
                    addBlock(buildQuotedBlock(position..closingQuotePos))
                    blockLeftIndex = closingQuotePos + 1
                    closingQuotePos + 1
                }
                '|' -> {
                    addBlock(buildUnquotedBlock(blockLeftIndex until position))
                    commitBlocks()
                    blockLeftIndex = position + 1
                    position + 1
                }
                else -> {
                    position + 1
                }
            }
        }
        commitLast()
    }
}
