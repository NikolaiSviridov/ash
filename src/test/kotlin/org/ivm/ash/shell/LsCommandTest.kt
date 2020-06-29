package org.ivm.ash.shell

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayOutputStream
import java.io.File

internal class LsCommandTest {
    private var environment = Environment(System.getenv())
    private var currentDirectory = File(System.getProperty("user.dir"))

    @Test
    fun execute() {

        val pathStart = "src/test/kotlin/org/ivm/ash/shell/"

        val args1 = listOf(pathStart + "testDir", pathStart + "testDir2")
        val command1 = LsCommand(args1)

        command1.setWorkingDirectory(currentDirectory)
        command1.setEnvironment(environment)
        command1.execute()

        val expected1:String = currentDirectory.path + "/" + pathStart + "testDir:\n" +
                "1\n" +
                "2\n" +
                "ls: cannot access 'testDir2': No such file or directory\n"

        val output1 = command1.getOutput()
        val strOutput1 = output1?.let { String(it) }
        assertEquals(expected1, strOutput1)
    }
}