package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.input.Input
import com.riaancornelius.aoc2019.intcode.Computer

val inputString = Input().readText(5, false)
// Part 1:
println("Part 1: ${Computer(inputString).runCalculation(1)}")