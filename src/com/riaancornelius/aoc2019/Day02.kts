package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.input.Input
import com.riaancornelius.aoc2019.intcode.Computer

val inputString = Input().readText(2)
// Part 1:
println("Part 1: ${Computer(inputString).startCalculation(updates = *arrayOf(Pair(1, 12L), Pair(2, 2L)))}")

// Part 2:
val expectedOutput = 19690720L
// Need to figure out how to avoid the fact that they are nullable
val noun = (0L..99).findLast {
    run {
    val computer = Computer(inputString)
    computer.startCalculation(updates = *arrayOf(Pair(1, it), Pair(2, 0L)))
    computer.read(0, Computer.Mode.IMMEDIATE)
} < expectedOutput }
val verb = (0L..99).find {
    noun?.let { it1 -> run {
        val computer = Computer(inputString)
        computer.startCalculation(updates = *arrayOf(Pair(1, it1), Pair(2, it)))
        computer.read(0, Computer.Mode.IMMEDIATE)
    } } == expectedOutput
}
println()
println("Part 2: $noun")
println("Part 2: $verb")
println("Part 2: ${100 * noun!! + verb!!}")