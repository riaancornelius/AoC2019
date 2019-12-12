package com.riaancornelius.aoc2019.intcode

class Computer(private val inputString :String, private val debug :Boolean = false) {

    private val instructions = readInput()
    
    private fun readInput(): IntArray {
        return inputString.trim()
            .split(",")
            .map { it.toInt() }
            .toIntArray()
    }

    fun runCalculation(input :IntArray = arrayOf(0).toIntArray(),
                       vararg updates :Pair<Int, Int> ): MutableList<Int> {
        updates.forEach { instructions[it.first] = it.second }

        val output = mutableListOf<Int>()
        var pointer = 0
        var inputPointer = 0
        programLoop@ while (pointer < instructions.size) {
            val currentInstruction = Instruction.parse(instructions[pointer])
            val currentOpCode = currentInstruction.opCode

            if (debug) println("Checking position [$pointer] with value ${instructions[pointer]} {$currentInstruction}")
            if (debug) print("${instructions[pointer]} ")

            when (currentOpCode){
                1 -> {
                    if (debug) print(" -> ${instructions[pointer + 1]}, ${instructions[pointer + 2]}, ${instructions[pointer + 3]}")
                    val param1 = read(pointer + 1, currentInstruction.mode1)
                    val param2 = read(pointer + 2, currentInstruction.mode2)
                    write(pointer + 3, param1 + param2)
                    pointer += 4
                }
                2 -> {
                    if (debug) print(" -> ${instructions[pointer + 1]}, ${instructions[pointer + 2]}, ${instructions[pointer + 3]}")
                    val param1 = read(pointer + 1, currentInstruction.mode1)
                    val param2 = read(pointer + 2, currentInstruction.mode2)
                    write(pointer + 3, param1 * param2)
                    pointer += 4
                }
                3 -> {
                    if (debug) print(" -> ${instructions[pointer + 1]}, input[$inputPointer] = ${input[inputPointer]}")
                    write(pointer + 1, input[inputPointer++])
                    pointer += 2
                }
                4 -> {
                    if (debug) print(" -> ${instructions[pointer + 1]}")
                    output.add(read(pointer + 1, currentInstruction.mode1))
                    pointer += 2
                }
                5 -> {
                    if (debug) print(" -> ${instructions[pointer + 1]}, ${instructions[pointer + 2]}")
                    val condition = read(pointer + 1, currentInstruction.mode1)
                    if (condition != 0) {
                        pointer = read(pointer + 2, currentInstruction.mode2)
                        if (debug) print("Setting pointer to $pointer")
                    } else
                        pointer += 3
                }
                6 -> {
                    if (debug) print(" -> ${instructions[pointer + 1]}, ${instructions[pointer + 2]}")
                    val condition = read(pointer + 1, currentInstruction.mode1)
                    if (condition == 0) {
                        pointer = read(pointer + 2, currentInstruction.mode2)
                        if (debug) print("Setting pointer to $pointer")
                    } else
                        pointer += 3
                }
                7 -> {
                    if (debug) print(" -> ${instructions[pointer + 1]}, ${instructions[pointer + 2]}, ${instructions[pointer + 3]}")
                    val param1 = read(pointer +1, currentInstruction.mode1)
                    val param2 = read(pointer +2, currentInstruction.mode2)
                    write(pointer +3, run {if(param1 < param2) 1 else 0})
                    pointer += 4
                }
                8 -> {
                    if (debug) print(" -> ${instructions[pointer + 1]}, ${instructions[pointer + 2]}, ${instructions[pointer + 3]}")
                    val param1 = read(pointer +1, currentInstruction.mode1)
                    val param2 = read(pointer +2, currentInstruction.mode2)
                    write(pointer + 3, run {if(param1 == param2) 1 else 0})
                    pointer += 4
                }
                99 -> break@programLoop
                else -> throw RuntimeException("Unsupported OpCode found [$currentOpCode]")
            }
            if (debug) println("\n")
        }

        println()
        return output
    }

    fun read(position :Int, mode:Mode = Mode.POSITION) :Int {
        val value = if (mode == Mode.IMMEDIATE) instructions[position] else instructions[instructions[position]]
        if (debug) print( " << read value $value ")
        return value
    }

    private fun write(position :Int, value :Int){
        if (debug) print( " >> writing $value to position [${instructions[position]}]")
        instructions[instructions[position]] = value
    }

    data class Instruction(val opCode :Int, val mode1 : Mode, val mode2 : Mode, val mode3 : Mode) {
        companion object {
            fun parse(input: Int) : Instruction {
                val string = input.toString().padStart(5, '0')
//                println("Parsing $input padded to $string")
                return Instruction(
                    string.substring(string.lastIndex-1, string.length).toInt(),
                    when(string[2]) {
                        '1'  -> Mode.IMMEDIATE
                        else -> Mode.POSITION
                    },
                    when(string[1]) {
                        '1'  -> Mode.IMMEDIATE
                        else -> Mode.POSITION
                    },
                    when(string[0]) {
                        '1'  -> Mode.IMMEDIATE
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
                99 -> "TERMINATE"
                else -> "UNKNOWN"
            }
            return "Instruction($description: opCode=$opCode, mode1=$mode1, mode2=$mode2, mode3=$mode3)"
        }
    }

    enum class Mode {
        POSITION,
        IMMEDIATE
    }
}