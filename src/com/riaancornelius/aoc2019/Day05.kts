package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.collection.Queue
import com.riaancornelius.aoc2019.input.Input
import com.riaancornelius.aoc2019.intcode.Computer

val inputString = Input().readText(5, false)
// Part 1:
println("Part 1: ${Computer(inputString, true).runCalculation(Queue(mutableListOf(1L)))}")
println(" ====================================================== ")

// Part 2:
val exampleInputString = Input().readText(5, true)
println("Part 2 (test) Input 7: ${Computer(exampleInputString, debug = false).runCalculation(Queue(mutableListOf(7L)))}")
println("Part 2 (test) Input 8: ${Computer(exampleInputString).runCalculation(Queue(mutableListOf(8L)))}")
println("Part 2 (test) Input 9: ${Computer(exampleInputString).runCalculation(Queue(mutableListOf(9L)))}")
println(" ====================================================== ")

println("Part 2: ${Computer(inputString).runCalculation(Queue(mutableListOf(5L)))}")

