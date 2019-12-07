package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.input.Input
import kotlin.math.max
import kotlin.math.truncate

class Day01b {

    fun readFileLineByLineUsingForEachLine() : Int {
        var fuelRequired = 0
        val input = Input()
        input.getFile(1, false).forEachLine {
            fuelRequired += calculateFuelIncludingFuel(
                calculateFuelForModule(it.toDouble()))
        }
//        println("Part1: Total fuel required: $fuelRequired")
        return fuelRequired
    }

    private fun calculateFuelForModule(mass: Double): Int {
        // mass, divide by three, round down, and subtract 2.
        val fuelRequired = truncate(mass / 3).toInt() - 2
//        println("Module weight: $mass requires: $fuelRequired")
        return fuelRequired
    }

    private fun calculateFuelIncludingFuel(initialFuel: Int): Int {
        var newFuelRequired :Int = Day01b().calculateFuelForModule(initialFuel.toDouble())
        var totalFuelRequired :Int = newFuelRequired
        while (newFuelRequired > 0){
            newFuelRequired = Day01b().calculateFuelForModule(newFuelRequired.toDouble())
            totalFuelRequired += max(newFuelRequired, 0)
        }
        totalFuelRequired += initialFuel
//        println("    Including fuel: $totalFuelRequired")
        return totalFuelRequired
    }
}

val totalFuelRequired = Day01b().readFileLineByLineUsingForEachLine()
println("Part 2: Total fuel required: $totalFuelRequired")