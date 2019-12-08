package com.riaancornelius.aoc2019.instructions

import com.riaancornelius.aoc2019.input.Input
import java.lang.RuntimeException

class Computer(private val inputString :String) {

    private val instructions = readInput()
    
    private fun readInput(): IntArray {
        return inputString.trim()
            .split(",")
            .map { it.toInt() }
            .toIntArray()
    }

    fun runCalculation(noun: Int, verb:Int): Int {
        instructions[1] = noun
        instructions[2] = verb

        program@ for (index in instructions.indices step 4) {
//            instructions.forEach { print("${it.toString().padEnd(7)} ") }
//            println()
            when (instructions[index]){
                1 -> opCode1(instructions[index+1], instructions[index+2], instructions[index+3])
                2 -> opCode2(instructions[index+1], instructions[index+2], instructions[index+3])
                99 -> break@program
                else -> throw RuntimeException("You screwed up something")
            }
        }
        return instructions[0]
    }

    private fun opCode1(position1: Int, position2: Int, outPutPosition: Int) {
        val output = instructions[position1] + instructions[position2]
        instructions[outPutPosition] = output
    }

    private fun opCode2(position1: Int, position2: Int, outPutPosition: Int) {
        val output = instructions[position1] * instructions[position2]
        instructions[outPutPosition] = output
    }
}