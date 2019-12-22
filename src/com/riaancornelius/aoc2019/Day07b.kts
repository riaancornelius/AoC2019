package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.collection.Queue
import com.riaancornelius.aoc2019.input.Input
import com.riaancornelius.aoc2019.intcode.Computer

val phases = Input().permutations("56789")
val instructions= Input().readText(7)

val values = mutableMapOf<String, Long>()
phases.forEach {
//    println("Running $it")
    val inputValues = it.map { char -> char.toString().toLong() }
    println("Running $inputValues")
    var stillRunning = true
    val i0 = Computer(instructions)
    val i1 = Computer(instructions)
    val i2 = Computer(instructions)
    val i3 = Computer(instructions)
    val i4 = Computer(instructions)

    val inputs = Queue(mutableListOf(inputValues[0], 0L))

    var out0 = i0.startCalculation(inputs)
    stillRunning = stillRunning && !i0.completed
    inputs.enqueue(listOf(inputValues[1]))
    inputs.enqueue(out0)

    var out1 = i1.startCalculation(inputs)
    stillRunning = stillRunning && !i1.completed
    inputs.enqueue(listOf(inputValues[2]))
    inputs.enqueue(out1)

    var out2 = i2.startCalculation(inputs)
    stillRunning = stillRunning && !i2.completed
    inputs.enqueue(listOf(inputValues[3]))
    inputs.enqueue(out2)

    var out3 = i3.startCalculation(inputs)
    stillRunning = stillRunning && !i3.completed
    inputs.enqueue(listOf(inputValues[4]))
    inputs.enqueue(out3)

    var out4 = i4.startCalculation(inputs)
    stillRunning = stillRunning && !i4.completed
    inputs.enqueue(out4)

    do {
        out0 = i0.runCalculation(inputs)
        stillRunning = stillRunning && !i0.completed
        inputs.enqueue(out0)

        out1 = i1.runCalculation(inputs)
        stillRunning = stillRunning && !i1.completed
        inputs.enqueue(out1)

        out2 = i2.runCalculation(inputs)
        stillRunning = stillRunning && !i2.completed
        inputs.enqueue(out2)

        out3 = i3.runCalculation(inputs)
        stillRunning = stillRunning && !i3.completed
        inputs.enqueue(out3)

        out4 = i4.runCalculation(inputs)
        stillRunning = stillRunning && !i4.completed
        println("stillRunning: $stillRunning and last output was $out4")
        inputs.enqueue(out4)
    } while (stillRunning)
    values[it] = out4[0]
}

val (phase, boost) = values.maxBy { it.value }!!
println("Biggest boost ($boost) is for phase: $phase ")