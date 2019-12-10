package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.input.Input
import com.riaancornelius.aoc2019.intcode.Computer

val inputString = Input().readText(2)
// Part 1:
println("Part 1: ${Computer(inputString).runCalculation(Pair(1, 12), Pair(2, 2))}")

// Part 2:
val expectedOutput = 19690720
// Need to figure out how to avoid the fact that they are nullable
val noun = (0..99).findLast { Computer(inputString).runCalculation(Pair(1, it), Pair(2, 0)) < expectedOutput }
val verb = (0..99).find { noun?.let { it1 ->
    Computer(inputString).runCalculation(Pair(1, it1), Pair(2, it)) } == expectedOutput }
println()
println("Part 2: $noun")
println("Part 2: $verb")
println("Part 2: ${100 * noun!! + verb!!}")