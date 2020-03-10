package org.ivm.ash.shell

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.*

// -i (нечувствительность к регистру),
// -w (поиск только слов целиком),
// -A n (распечатать n строк после строки с совпадением);
// поддерживающую регулярные выражения в строке поиска;

class GrepCommandArgumentsParser(private val argumentList: ArgumentList): CliktCommand(name="grep") {
    val ignoreCase by option("--ignore-case", "-i").flag(default=false)
    val searchWords by option("--words", "-w").flag(default=false)
    val printLinesAfter by option("-A").int().default(0)
    val pattern by argument()
    val inputFile by argument()

    init {
        parse(argumentList)
    }

    override fun run() {}
}
