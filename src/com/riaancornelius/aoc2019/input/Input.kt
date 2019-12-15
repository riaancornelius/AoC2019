package com.riaancornelius.aoc2019.input

import java.io.File
import java.util.stream.Stream

class Input {

    fun readLines(day: Int, example: Boolean = false): Stream<String> {
        return getFile(day, example).inputStream().bufferedReader().lines()!!
    }

    fun readText(day: Int, example: Boolean = false): String {
        return getFile(day, example).readText()
    }

    fun getFile(day: Int, example: Boolean = false): File {
        val dayString = day.toString().padStart(2, '0')
        val fileString = if (example) "example" else "input"
        return File("./input/${dayString}_$fileString.txt")
    }

    fun permutations(word :String) : List<String> {
        val list = word.toCharArray().toMutableList()
        val permList = permute(list)
        val distPerList = permList.distinct().map { it.joinToString("") }
        println("There are ${distPerList.size} permutations of $word \n")
//        for (perm in distPerList)
//            println(perm)
        return distPerList
    }

    private fun <String> permute(list:List <String>):List<List<String>>{
        if(list.size==1) return listOf(list)
        val perms=mutableListOf<List <String>>()
        val sub=list[0]
        for(perm in permute(list.drop(1)))
            for (i in 0..perm.size){
                val newPerm=perm.toMutableList()
                newPerm.add(i,sub)
                perms.add(newPerm)
            }
        return perms
    }
}