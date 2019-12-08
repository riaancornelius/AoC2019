package com.riaancornelius.aoc2019

import com.riaancornelius.aoc2019.input.Input
import java.lang.RuntimeException

class Day08b {
    private var input = readInput()

    private fun readInput(): String {
        return Input().readText(8).trim()
    }

    fun decode(width: Int, height:Int) {
        val charsPerLayer = width * height
        val chunked = input.chunked(charsPerLayer)

        var result = ""
        pixel@ for (pixel in 0 until charsPerLayer) {
            layer@ for (layer in chunked) {
                when (layer[pixel]) {
                    '0' -> {
                        result = result.plus(' '); continue@pixel
                    }
                    '1' -> {
                        result = result.plus('#'); continue@pixel
                    }
                    else -> continue@layer
                }
            }
        }

        for (index in 0 until result.length step width) {
            println("${result.subSequence(index, index+width)}")
        }

    }
}

// Part 2:
Day08b().decode(25, 6)