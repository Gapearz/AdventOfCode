package Day05_Cafeteria

import Tools.FileReaderAdvent
import kotlin.system.measureTimeMillis

fun main(){
    val fileReaderAdvent = FileReaderAdvent("src/Day05_Cafeteria/input.txt")
    val input = fileReaderAdvent.readFile()
    val emptyLineIndex = input.indexOfFirst { it.isBlank() }
    val freshList = input.take(emptyLineIndex).map {
        val (first, second) = it.split("-")
        Pair(first.toLong(), second.toLong())
    }
    val ingredientList = input.drop(emptyLineIndex + 1).map { it.toLong() }

    val time1 = measureTimeMillis {
        val result = part1(freshList, ingredientList)
        println("Available fresh ingredients: $result")
    }
    println("Part 1 took: $time1 ms")

    val time2 = measureTimeMillis {
        val result = part2(freshList)
        println("ID's that are considered to be fresh: $result")
    }
    println("Part 2 took: $time2 ms")
}

fun part1(freshList: List<Pair<Long, Long>>, ingredientList: List<Long>): Int{
    var availableFresh = 0

    for(ingredient in ingredientList){
        for(range in freshList){
            if(ingredient in range.first..range.second){
                availableFresh++
                break
            }
        }
    }

    return availableFresh
}

fun part2(freshList: List<Pair<Long, Long>>): Long {
    val sortedRanges = freshList.sortedBy { it.first }
    val mergedRanges = mutableListOf<Pair<Long, Long>>()

    var currentRange = sortedRanges.first()

    for (range in sortedRanges.drop(1)) {
        if (range.first <= currentRange.second + 1) {
            currentRange = Pair(currentRange.first, maxOf(currentRange.second, range.second))
        } else {
            mergedRanges.add(currentRange)
            currentRange = range
        }
    }
    mergedRanges.add(currentRange)

    return mergedRanges.sumOf { it.second - it.first + 1 }
}