package com.riaancornelius.aoc2019.intcode

class Computer(private val inputString :String) {

    private val instructions = readInput()
    
    private fun readInput(): IntArray {
        return inputString.trim()
            .split(",")
            .map { it.toInt() }
            .toIntArray()
    }
    fun runCalculation(vararg updates :Pair<Int, Int> ): Int {
        updates.forEach { instructions[it.first] = it.second }

        var pointer = 0
        programLoop@ while (pointer < instructions.size) {
            val currentInstruction = Instruction.parse(instructions[pointer])
            val currentOpCode = currentInstruction.opCode

            println("Checking position [$pointer] with value $currentInstruction")
            pointer += when (currentOpCode){
                1 -> {
                    opCode1(instructions[pointer+1], instructions[pointer+2], instructions[pointer+3])
                    4
                }
                2 -> {
                    opCode2(instructions[pointer+1], instructions[pointer+2], instructions[pointer+3])
                    4
                }
                3 -> {
                    2
                }
                4 -> {
                    2
                }
                99 -> break@programLoop
                else -> throw RuntimeException("Unsupported OpCode found [$currentOpCode]")
            }
        }

        return instructions[0]
    }

    private fun opCode1(position1: Int, position2: Int, outPutPosition: Int) {
        val output = instructions[position1] + instructions[position2]
        instructions[outPutPosition] = output
    }

    private fun opCode2(position1: Int, position2: Int, outPutPosition: Int) {
        val output = instructions[position1] * instructions[position2]
        instructions[outPutPosition] = output
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
            return "Instruction(opCode=$opCode, mode1=$mode1, mode2=$mode2, mode3=$mode3)"
        }
    }

    enum class Mode {
        POSITION,
        IMMEDIATE
    }
}