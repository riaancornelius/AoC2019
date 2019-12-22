package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.collection.Queue
import com.riaancornelius.aoc2019.input.Input
import com.riaancornelius.aoc2019.intcode.Computer

// Testing
println("Test 1: ${Computer("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99").startCalculation()}")
println("Test 2: ${Computer("1102,34915192,34915192,7,4,7,99,0").startCalculation(Queue(mutableListOf(1L)))}")
println("Test 3: ${Computer("104,1125899906842624,99").startCalculation(Queue(mutableListOf(1L)))}")

// Part 1:
val inputString = Input().readText(9)
println("Part 1: ${Computer(inputString).startCalculation(Queue(mutableListOf(1L)))}")

// Part 2:
println("Part 2: ${Computer(inputString).runCalculation(Queue(mutableListOf(2L)))}")

