package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.input.Input
import kotlin.math.truncate

class Day01b {

    fun readFileLineByLineUsingForEachLine() : Int {
        val input = Input()
        return input.readLines(1, false)
            .mapToInt{ calculateFuelForModule(it.toDouble()) }
            .map { calculateFuelIncludingFuel(it, 0) }
            .sum()
    }

    private fun calculateFuelForModule(mass: Double): Int {
        return truncate(mass / 3).toInt() - 2
    }

    private tailrec fun calculateFuelIncludingFuel(newFuelRequired: Int, totalFuel: Int): Int {
        println("  recursion: $newFuelRequired with total: $totalFuel")
        return if (newFuelRequired <= 0) {
            println("    returning $totalFuel")
            totalFuel
        } else {
            calculateFuelIncludingFuel(Day01b().calculateFuelForModule(newFuelRequired.toDouble()),
                totalFuel + newFuelRequired)
        }
    }
}

val totalFuelRequired = Day01b().readFileLineByLineUsingForEachLine()
println("\nPart 2: Total fuel required: $totalFuelRequired")