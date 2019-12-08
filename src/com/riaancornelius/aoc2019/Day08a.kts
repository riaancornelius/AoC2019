package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.input.Input

class Day08a {
    private var input = readInput()

    private fun readInput(): String {
        return Input().readText(8, false).trim()
    }

    fun decode(width: Int, height:Int) {
        val charsPerLayer = width * height
        val chunked = input.chunked(charsPerLayer)
            .mapIndexed{ idx, it -> idx to it}.toMap()
//        chunked.entries.forEach { entry -> println("[${entry.key}] ${entry.value}")}

        val minIndex = chunked
            .mapValues { it.value.count { char -> char=='0' } }
            .minBy { it.value }?.key

        println("least 0's: [$minIndex] ${chunked[minIndex]}")

        val grouping = chunked[minIndex]
            ?.groupingBy { it }
            ?.eachCount()

        println("result: ${grouping?.getValue('1')?.times(grouping.getValue('2'))}")
    }
}

// Part 1:
Day08a().decode(25, 6)