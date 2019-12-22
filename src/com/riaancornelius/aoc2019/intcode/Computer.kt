package com.riaancornelius.aoc2019.intcode

import com.riaancornelius.aoc2019.collection.Queue
import java.lang.Math.floor

class Computer(private val inputString :String, private val debug :Boolean = false) {

    var completed = false
    private var pointer = 0
    private var relativeBase = 0

    private var instructions = readInput()
    
    private fun readInput(): LongArray {
        return inputString.trim()
            .split(",")
            .map { it.toLong() }
            .toLongArray()
    }

    fun startCalculation(
        input: Queue<Long> = Queue(),
        vararg updates: Pair<Int, Long>): MutableList<Long> {
//        print("Updates: ")
//        updates.forEach { print("$it, ") }
//        println("")
        updates.forEach { instructions[it.first] = it.second }

        return runCalculation(input)
    }

    fun runCalculation(input :Queue<Long> = Queue()): MutableList<Long> {
//        print("Inputs: ")
//        input.forEach { print("$it, ") }
//        println("")

        val output = mutableListOf<Long>()

        programLoop@ while (pointer < instructions.size) {
            val currentInstruction = Instruction.parse(instructions[pointer])
            val currentOpCode = currentInstruction.opCode

            if (debug) println("Checking position [$pointer] with value ${instructions[pointer]} {$currentInstruction}")
            if (debug) print("[$pointer]: ${instructions[pointer]} ")

            when (currentOpCode){
                1 -> {
                    if (debug) print(" -> [${pointer + 1}]: ${instructions[pointer + 1]}, " +
                            "[${pointer + 2}]: ${instructions[pointer + 2]}, " +
                            "[${pointer + 3}]: ${instructions[pointer + 3]}")
                    val param1 = read(pointer + 1, currentInstruction.mode1)
                    val param2 = read(pointer + 2, currentInstruction.mode2)
                    write(pointer + 3, param1 + param2, currentInstruction.mode3)
                    pointer += 4
                }
                2 -> {
                    if (debug) print(" -> [${pointer + 1}]: ${instructions[pointer + 1]}, " +
                            "[${pointer + 2}]: ${instructions[pointer + 2]}, " +
                            "[${pointer + 3}]: ${instructions[pointer + 3]}")
                    val param1 = read(pointer + 1, currentInstruction.mode1)
                    val param2 = read(pointer + 2, currentInstruction.mode2)
                    write(pointer + 3, param1 * param2, currentInstruction.mode3)
                    pointer += 4
                }
                3 -> {
                    val peek = input.peek()
                    if (debug) {
                        print(" -> [${pointer + 1}]: ${instructions[pointer + 1]}, input.peek() = $peek")
                    }
                    if (peek != null) {
                        write(pointer + 1, input.dequeue()!!, currentInstruction.mode1)
                        pointer += 2
                    } else {
                        // Suspend program if there isn't any inputs available
                        break@programLoop
                    }
                }
                4 -> {
                    if (debug) print(" -> [${pointer + 1}]: ${instructions[pointer + 1]}")
                    output.add(read(pointer + 1, currentInstruction.mode1))
                    pointer += 2
                }
                5 -> {
                    if (debug) print(" -> [${pointer + 1}]: ${instructions[pointer + 1]}, " +
                            "[${pointer + 2}]: ${instructions[pointer + 2]} ")
                    val condition = read(pointer + 1, currentInstruction.mode1)
                    if (condition != 0L) {
                        pointer = read(pointer + 2, currentInstruction.mode2).toInt()
                        if (debug) print("Setting pointer to $pointer")
                    } else
                        pointer += 3
                }
                6 -> {
                    if (debug) print(" -> [${pointer + 1}]: ${instructions[pointer + 1]}, " +
                            "[${pointer + 2}]: ${instructions[pointer + 2]} ")
                    val condition = read(pointer + 1, currentInstruction.mode1)
                    if (condition == 0L) {
                        pointer = read(pointer + 2, currentInstruction.mode2).toInt()
                        if (debug) print("Setting pointer to $pointer")
                    } else
                        pointer += 3
                }
                7 -> {
                    if (debug) print(" -> [${pointer + 1}]: ${instructions[pointer + 1]}, " +
                            "[${pointer + 2}]: ${instructions[pointer + 2]}, " +
                            "[${pointer + 3}]: ${instructions[pointer + 3]}")
                    val param1 = read(pointer +1, currentInstruction.mode1)
                    val param2 = read(pointer +2, currentInstruction.mode2)
                    write(pointer +3, run {if(param1 < param2) 1L else 0L}, currentInstruction.mode3)
                    pointer += 4
                }
                8 -> {
                    if (debug) print(" -> [${pointer + 1}]: ${instructions[pointer + 1]}, " +
                            "[${pointer + 2}]: ${instructions[pointer + 2]}, " +
                            "[${pointer + 3}]: ${instructions[pointer + 3]}")
                    val param1 = read(pointer +1, currentInstruction.mode1)
                    val param2 = read(pointer +2, currentInstruction.mode2)
                    write(pointer + 3, run {if(param1 == param2) 1L else 0L}, currentInstruction.mode3)
                    pointer += 4
                }
                9 -> {
                    val param1 = read(pointer +1, currentInstruction.mode1)
                    relativeBase += param1.toInt()
                    if (debug) print(" -> [${pointer + 1}]: ${instructions[pointer + 1]} -> relativeBase adjusted to $relativeBase")
                    pointer += 2
                }
                99 -> {
                    this.completed = true
                    break@programLoop
                }
                else -> throw RuntimeException("Unsupported OpCode found [$currentOpCode]")
            }
            if (debug) println("\n")
        }

        if (debug) println()
        return output
    }

    fun read(position :Int, mode:Mode = Mode.POSITION) :Long {
        // Make sure we can at least access position + relativeBase
        if (position >= instructions.size) {
            grow(position)
        }
        val value = when (mode){
            Mode.IMMEDIATE -> {
                instructions[position]
            }
            Mode.RELATIVE -> {
                val indexRead = relativeBase + instructions[position].toInt()
                if (indexRead >= instructions.size) {
                    grow(indexRead)
                }
                if (debug) print (" .. relativeBase = $relativeBase, reading value at [${relativeBase + instructions[position]}]")
                instructions[indexRead]
            }
            Mode.POSITION -> {
                val indexRead = instructions[position].toInt()
                if (indexRead >= instructions.size) {
                    grow(indexRead)
                }
                instructions[indexRead]
            }
        }
        if (debug) print( " << read value $value from position [$position] with mode $mode ")
        return value
    }

    private fun write(position :Int, value :Long, mode:Mode = Mode.IMMEDIATE){
        if (debug) print( " >> writing to position [$position] in $mode")
        if (position >= instructions.size) {
            grow(position)
        }
        when (mode) {
            Mode.IMMEDIATE -> {
                instructions[position] = value
            }
            Mode.POSITION -> {
                val index = instructions[position].toInt()
                if (index >= instructions.size) {
                    grow(index)
                }
                instructions[index] = value
            }
            Mode.RELATIVE -> {
                val index = instructions[position].toInt() + relativeBase
                if (index >= instructions.size) {
                    grow(index)
                }
                instructions[index] = value
            }
        }
    }

    private fun grow(maxIndex :Int) {
        val newSize =  floor(maxIndex * 1.5).toInt()
        if (debug) print(" -- growing to $newSize -- ")
        val newArray = LongArray(newSize)
        (0 until instructions.size).forEach { index-> newArray[index] = instructions[index] }
        (instructions.size until newSize).forEach { index-> newArray[index] = 0 }
        instructions = newArray
    }

    data class Instruction(val opCode :Int, val mode1 : Mode, val mode2 : Mode, val mode3 : Mode) {
        companion object {
            fun parse(input: Long) : Instruction {
                val string = input.toString().padStart(5, '0')
//                println("Parsing $input padded to $string")
                return Instruction(
                    string.substring(string.lastIndex-1, string.length).toInt(),
                    when(string[2]) {
                        '1'  -> Mode.IMMEDIATE
                        '2'  -> Mode.RELATIVE
                        else -> Mode.POSITION
                    },
                    when(string[1]) {
                        '1'  -> Mode.IMMEDIATE
                        '2'  -> Mode.RELATIVE
                        else -> Mode.POSITION
                    },
                    when(string[0]) {
                        '1'  -> Mode.IMMEDIATE
                        '2'  -> Mode.RELATIVE
                        else -> Mode.POSITION
                    }
                )
            }
        }

        override fun toString(): String {
            val description = when (opCode) {
                1 -> "ADDITION"
                2 -> "MULTIPLICATION"
                3 -> "READ_INPUT"
                4 -> "WRITE_OUTPUT"
                5 -> "JUMP_IF_TRUE"
                6 -> "JUMP_IF_FALSE"
                7 -> "LESS_THAN"
                8 -> "EQUALS"
                9 -> "ADJUST_RELATIVE_BASE"
                99 -> "TERMINATE"
                else -> "UNKNOWN"
            }
            return "Instruction($description: opCode=$opCode, mode1=$mode1, mode2=$mode2, mode3=$mode3)"
        }
    }

    enum class Mode {
        POSITION,
        IMMEDIATE,
        RELATIVE
    }
}