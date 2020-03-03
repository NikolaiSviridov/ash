package org.ivm.ash.parser

import org.ivm.ash.parser.unused.Block
import org.ivm.ash.parser.unused.BlockType
import org.ivm.ash.parser.unused.NodeSplitter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*

class NodeSplitterTest {
    @Test
    fun splitByNodesBasic() {
        val testInput = "some | more | next"
        val splitter = NodeSplitter(testInput)
        splitter.split()
        val expected = listOf(
            listOf(Block("some", BlockType.unquoted)),
            listOf(Block("more", BlockType.unquoted)),
            listOf(Block("next", BlockType.unquoted))
        )
        assertEquals(expected, splitter.nodes)
    }

    @Test
    fun splitByNodesSimple() {
        val testInput = "some sdg | string more | next"
        val splitter = NodeSplitter(testInput)
        splitter.split()
        val expected = listOf(
            listOf(Block("some sdg", BlockType.unquoted)),
            listOf(Block("string more", BlockType.unquoted)),
            listOf(Block("next", BlockType.unquoted))
        )
        assertEquals(expected, splitter.nodes)
    }

    @Test
    fun splitByNodesWithoutSpaces() {
        val testInput = "some sdg| string more|next"
        val splitter = NodeSplitter(testInput)
        splitter.split()
        val expected = listOf(
            listOf(Block("some sdg", BlockType.unquoted)),
            listOf(Block("string more", BlockType.unquoted)),
            listOf(Block("next", BlockType.unquoted))
        )
        assertEquals(expected, splitter.nodes)
    }

    @Test
    fun splitByNodesQuotedPipe() {
        val testInput = "some 'quoted | string' more | next"
        val splitter = NodeSplitter(testInput)
        splitter.split()
        val expected = listOf(
            listOf(
                Block("some", BlockType.unquoted),
                Block("'quoted | string'", BlockType.squoted),
                Block("more", BlockType.unquoted)
            ),
            listOf(Block("next", BlockType.unquoted))
        )
        assertEquals(expected, splitter.nodes)
    }

    @Test
    fun splitByNodesMultipleBlocks() {
        val testInput = "some 'quoted | string' \"second block\" more | 'sdf' next"
        val splitter = NodeSplitter(testInput)
        splitter.split()
        val firstNode = listOf(
            Block("some", BlockType.unquoted),
            Block("'quoted | string'", BlockType.squoted),
            Block("\"second block\"", BlockType.dquoted),
            Block("more", BlockType.unquoted)
        )
        val secondNode = listOf(
            Block("'sdf'", BlockType.squoted),
            Block("next", BlockType.unquoted)
        )
        assertEquals(listOf(firstNode, secondNode), splitter.nodes)
    }
}