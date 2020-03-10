package org.ivm.ash.shell

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.*
import java.lang.Exception


/**
 * Парсер аргументов для команды grep
 *
 * @param argumentList Список аргументов, которые нужно парсить
 */
class GrepCommandArgumentsParser(private val argumentList: ArgumentList): CliktCommand(name="grep") {
    val ignoreCase by option("--ignore-case", "-i").flag(default=false)
    val searchWords by option("--words", "-w").flag(default=false)
    val printLinesAfter by option("-A").int().default(0)
    val pattern by argument()
    val inputFile by argument().optional()

    /**
     * Запускает парсер на входном списке аргументов.
     */
    fun parse() {
        parse(argumentList)
    }

    override fun run() {}
}
