package org.ivm.ash

import org.ivm.ash.shell.Shell

fun main(args: Array<String>) {
    val shell = Shell(args.asList())
    shell.start()
}
