package org.ivm.ash.shell

import java.io.File

class DoNothingCommand: Command {
    override fun execute(input: ByteArray?) {
        TODO("Not yet implemented")
    }

    override fun getOutput(): ByteArray? {
        TODO("Not yet implemented")
    }

    override fun getExitCode(): Int {
        TODO("Not yet implemented")
    }

    override fun setWorkingDirectory(directory: File) {
        TODO("Not yet implemented")
    }

    override fun setEnvironment(environment: Environment) {
        TODO("Not yet implemented")
    }
}
