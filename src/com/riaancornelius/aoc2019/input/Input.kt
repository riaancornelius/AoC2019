package com.riaancornelius.aoc2019.input

import java.io.File
import java.util.stream.Stream

class Input {

    fun readLines(day: Int, example: Boolean = false): Stream<String> {
        return getFile(day, example).inputStream().bufferedReader().lines()!!
    }

    fun readText(day: Int, example: Boolean = false): String {
        return getFile(day, example).readText()
    }

    fun getFile(day: Int, example: Boolean = false): File {
        val dayString = day.toString().padStart(2, '0')
        val fileString = if (example) "example" else "input"
        return File("./input/${dayString}_$fileString.txt")
    }

}