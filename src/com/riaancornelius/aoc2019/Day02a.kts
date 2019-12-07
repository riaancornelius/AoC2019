package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.input.Input
import java.lang.RuntimeException

class Day02a {
    private var intCode = readInput()

    private fun readInput(): IntArray {
        return Input().readText(2, false).trim()
            .split(",")
            .map { it.toInt() }
            .toIntArray()
    }

    fun runCalculation() {
        intCode[1] = 12
        intCode[2] = 2
        program@ for (index in intCode.indices step 4) {
            intCode.forEach { print("${it.toString().padEnd(7)} ") }
            println()
            when (intCode[index]){
                1 -> opCode1(intCode[index+1], intCode[index+2], intCode[index+3])
                2 -> opCode2(intCode[index+1], intCode[index+2], intCode[index+3])
                99 -> break@program
                else -> throw RuntimeException("You screwed up something")
            }
        }
        println()
        println(intCode[0])
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

Day02a().runCalculation()
 