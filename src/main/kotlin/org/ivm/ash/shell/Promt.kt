package org.ivm.ash.shell

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class Promt(private val promtString: String = defaultPromtString) {
    private val inputStream = BufferedReader(InputStreamReader(System.`in`))

    companion object {
        const val defaultPromtString = "$ "
    }

    fun promtInput(directory: File?): String {
        if (directory != null) {
            print(directory.absolutePath)
            print(" ")
        }
        print(defaultPromtString)
        return inputStream.readLine()
    }
}
