package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.input.Input
import kotlin.math.truncate

class Day01a {

    fun readFileLineByLineUsingForEachLine() : Int {
        var fuelRequired = 0
        val input = Input()
        input.getFile(1, false).forEachLine { fuelRequired += calculateFuelForModule(it.toDouble()) }
//        println("Part1: Total fuel required: $fuelRequired")
        return fuelRequired
    }

    fun calculateFuelForModule(mass: Double): Int {
        // mass, divide by three, round down, and subtract 2.
        val fuelRequired = truncate(mass / 3).toInt() - 2
//        println("Module weight: $mass requires: $fuelRequired")
        return fuelRequired
    }
}

val initialFuel :Int = Day01a().readFileLineByLineUsingForEachLine()
println("Part 1: Total fuel required: $initialFuel")