package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.input.Input
import com.riaancornelius.aoc2019.intcode.Computer

val phases = Input().permutations("01234")
val instructions= Input().readText(7)

val values = mutableMapOf<String, Int>()
phases.forEach {
//    println("Running $it")
    val inputValues = it.map { char -> char.toString().toInt() }
    println("Running $inputValues")
    val i0 = Computer(instructions).runCalculation(input = listOf(inputValues[0], 0).toIntArray())
    val i1 = Computer(instructions).runCalculation(input = listOf(inputValues[1], i0[0]).toIntArray())
    val i2 = Computer(instructions).runCalculation(input = listOf(inputValues[2], i1[0]).toIntArray())
    val i3 = Computer(instructions).runCalculation(input = listOf(inputValues[3], i2[0]).toIntArray())
    val i4 = Computer(instructions).runCalculation(input = listOf(inputValues[4], i3[0]).toIntArray())
    println("$it = $i4[0]")
    values[it] = i4[0]
    println()
}

val (phase, boost) = values.maxBy { it.value }!!
println("Biggest boost ($boost) is for phase: $phase ")