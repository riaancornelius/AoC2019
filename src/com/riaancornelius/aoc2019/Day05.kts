package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.input.Input
import com.riaancornelius.aoc2019.intcode.Computer

val inputString = Input().readText(5, false)
// Part 1:
println("Part 1: ${Computer(inputString, true).runCalculation(arrayOf(1).toIntArray())}")
println(" ====================================================== ")

// Part 2:
val exampleInputString = Input().readText(5, true)
println("Part 2 (test) Input 7: ${Computer(exampleInputString, debug = false).runCalculation(arrayOf(7).toIntArray())}")
println("Part 2 (test) Input 8: ${Computer(exampleInputString).runCalculation(arrayOf(8).toIntArray())}")
println("Part 2 (test) Input 9: ${Computer(exampleInputString).runCalculation(arrayOf(9).toIntArray())}")
println(" ====================================================== ")

println("Part 2: ${Computer(inputString).runCalculation(arrayOf(5).toIntArray())}")

