package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.input.Input
import kotlin.math.truncate

class Day01a {

    fun readFileLineByLineUsingForEachLine() : Int {
        val input = Input()
        return input.readLines(1)
            .mapToInt{ calculateFuelForModule(it.toDouble()) }
            .sum()
    }

    private fun calculateFuelForModule(mass: Double): Int {
        // mass, divide by three, round down, and subtract 2.
        val fuelRequired = truncate(mass / 3).toInt() - 2
//        println("Module weight: $mass requires: $fuelRequired")
        return fuelRequired
    }
}

val initialFuel :Int = Day01a().readFileLineByLineUsingForEachLine()
println("Part 1: Total fuel required: $initialFuel")