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

        val expectedList1 = listOf(
            currentDirectory.resolve(pathStart + "testDir").absolutePath + ":\n",
            "1\n",
            "2\n",
            "testDir2\n",
            "ls: cannot access 'testDir2': No such file or directory\n"
        )

        val expected1 = ByteArrayOutputStream()
        expectedList1.forEach{
            expected1.write(it.toByteArray())
        }

        val output1 = command1.getOutput()

        assertArrayEquals(expected1.toByteArray(), output1)



        val args2 = listOf(pathStart + "testDir", pathStart + "testDir/2", pathStart + "testDir/testDir2")
        val command2 = LsCommand(args2)

        command2.setWorkingDirectory(currentDirectory)
        command2.setEnvironment(environment)
        command2.execute()

        val expectedList2 = listOf(
            currentDirectory.resolve(pathStart + "testDir").absolutePath + ":\n",
            "1\n",
            "2\n",
            "testDir2\n",
            "2\n",
            currentDirectory.resolve(pathStart + "testDir/testDir2").absolutePath + ":\n"
        )

        val expected2 = ByteArrayOutputStream()
        expectedList2.forEach{
            expected2.write(it.toByteArray())
        }

        val output2 = command2.getOutput()

        assertArrayEquals(expected2.toByteArray(), output2)

    }
}