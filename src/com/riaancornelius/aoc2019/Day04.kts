package com.riaancornelius.aoc2019

import kotlin.system.measureTimeMillis

val min = 264360
val max = 746325
var possiblePasswords = max - min
val timeMillis = measureTimeMillis {
    possiblePasswords = (min..max)
        .asSequence()
        .map { it.toString() }
        // Only keep values with at least one pair of adjacent character
        .filter { it.windowed(2).any { item -> item.toCharArray().distinct().size == 1 } }
        // Only keep sorted strings
        .filter { array -> array.zipWithNext { s1, s2 -> s1 <= s2 }.all { it } }
        .count()
}
println("Executed in $timeMillis ms")
println(possiblePasswords)
