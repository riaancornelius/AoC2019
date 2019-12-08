package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.graph.Node
import com.riaancornelius.aoc2019.graph.connectToNode
import com.riaancornelius.aoc2019.graph.findShortestDistanceVerbose
import com.riaancornelius.aoc2019.input.Input

class Day06b {

    fun calculate(): Int {
        val example = false

        val planets = mutableMapOf<String, Node>()

        Input().readLines(6, example)
            .map { it.split(")") }
            .forEach {
                planets[it[0]] = Node(it[0])
                planets[it[1]] = Node(it[1])
            }

//        planets.forEach { println("- $it") }

        Input().readLines(6, example)
            .map { it.split(")") }
            .forEach {
//                it.forEach { nodes -> println("  $nodes") }
                val a = planets[it[0]]!!
                val b = planets[it[1]]!!
                a.connectToNode(b, 1)
            }

        return planets["YOU"]!!.findShortestDistanceVerbose(planets["SAN"]!!)-2

    }
}
println("Transfers required: ${Day06b().calculate()}")