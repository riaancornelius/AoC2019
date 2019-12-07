package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.input.Input
import java.lang.RuntimeException

class Day02 {
    private var intCode = readInput()

    private fun readInput(): IntArray {
        return Input().readText(2, false).trim()
            .split(",")
            .map { it.toInt() }
            .toIntArray()
    }

    fun runCalculation(noun: Int, verb:Int): Int {
        intCode[1] = noun
        intCode[2] = verb

        program@ for (index in intCode.indices step 4) {
//            intCode.forEach { print("${it.toString().padEnd(7)} ") }
//            println()
            when (intCode[index]){
                1 -> opCode1(intCode[index+1], intCode[index+2], intCode[index+3])
                2 -> opCode2(intCode[index+1], intCode[index+2], intCode[index+3])
                99 -> break@program
                else -> throw RuntimeException("You screwed up something")
            }
        }
        return intCode[0]
    }

    private fun opCode1(position1: Int, position2: Int, outPutPosition: Int) {
        val output = intCode[position1] + intCode[position2]
        intCode[outPutPosition] = output
    }

    private fun opCode2(position1: Int, position2: Int, outPutPosition: Int) {
        val output = intCode[position1] * intCode[position2]
        intCode[outPutPosition] = output
    }
}

// Part 1:
//println(Day02().runCalculation(12, 2))

// Part 2:
val expectedOutput = 19690720
// Need to figure out how to avoid the fact that they are nullable
val noun = (0..99).findLast { Day02().runCalculation(it, 0) < expectedOutput }
val verb = (0..99).find { noun?.let { it1 -> Day02().runCalculation(it1, it) } == expectedOutput }

println("${100 * noun!! + verb!!}")