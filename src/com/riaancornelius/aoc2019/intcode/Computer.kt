package com.riaancornelius.aoc2019.intcode

class Computer(private val inputString :String) {

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

            println("Checking position [$pointer] with value $currentInstruction")
            pointer += when (currentOpCode){
                1 -> {
                    val param1 = read(pointer + 1, currentInstruction.mode1)
                    val param2 = read(pointer + 2, currentInstruction.mode2)
                    write(pointer + 3, param1 + param2)
                    println(" -> ${instructions[pointer + 1]}, ${instructions[pointer + 2]}, ${instructions[pointer + 3]}")
                    4
                }
                2 -> {
                    val param1 = read(pointer + 1, currentInstruction.mode1)
                    val param2 = read(pointer + 2, currentInstruction.mode2)
                    write(pointer + 3, param1 * param2)
                    println(" -> ${instructions[pointer + 1]}, ${instructions[pointer + 2]}, ${instructions[pointer + 3]}")
                    4
                }
                3 -> {
                    write(pointer + 1, input[inputPointer++])
                    println(" -> ${instructions[pointer + 1]}}")
                    2
                }
                4 -> {
                    println(" -> ${instructions[pointer + 1]}}")
                    output.add(read(pointer + 1, currentInstruction.mode1))
                    2
                }
                99 -> break@programLoop
                else -> throw RuntimeException("Unsupported OpCode found [$currentOpCode]")
            }
        }

        return output
    }

    fun read(position :Int, mode:Mode) :Int {
        return if (mode == Mode.IMMEDIATE) instructions[position] else instructions[instructions[position]]
    }

    private fun write(position :Int, value :Int){
        instructions[instructions[position]] = value
    }

    data class Instruction(val opCode :Int, val mode1 : Mode, val mode2 : Mode, val mode3 : Mode) {
        companion object {
            fun parse(input: Int) : Instruction {
                val string = input.toString().padStart(5, '0')
                println("Parsing $input padded to $string")
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
            return "Instruction(opCode=$opCode, mode1=$mode1, mode2=$mode2, mode3=$mode3)"
        }
    }

    enum class Mode {
        POSITION,
        IMMEDIATE
    }
}