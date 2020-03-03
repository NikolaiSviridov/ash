package org.ivm.ash.shell

data class CommandExecutionResult(var stdout: String, var stderr: String, var code: Int) {}
