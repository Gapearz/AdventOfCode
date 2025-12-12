package Day12_ChristmasTreeFarm

import Tools.FileReaderAdvent
import kotlin.system.measureTimeMillis

fun main(){
    val fileReaderAdvent = FileReaderAdvent("src/Day12_ChristmasTreeFarm/input.txt")
    val input = fileReaderAdvent.readFile()

    val time1 = measureTimeMillis {
        val result = part1(input)
        println("[part 1] Regions that can fit all of the presents: $result")
    }
    println("Part 1 took: $time1 ms")
    val time2 = measureTimeMillis {
        val result = part2()
        println("[part 2]: $result")
    }
    println("Part 2 took: $time2 ms")
}

fun part1(input: List<String>): Int {
    var fitsAllPresents = 0

    val presents = parsePresents(input)
    val areas = parseAreas(input)

    for(area in areas){
        var presentsSize = 0
        for(i in 0 until area.second.size){
            presentsSize += presents[i] * area.second[i]
        }
        if(presentsSize <= area.first.first * area.first.second){
            fitsAllPresents++
        }
    }

    return fitsAllPresents
}

fun part2(): String{
    return "Freebie!"
}

fun parsePresents(input: List<String>): List<Int> {
    val result = mutableListOf<Int>()
    var count = 0
    for (line in input) {
        if (line.matches(Regex("\\d+:.*"))) {
            if (result.isNotEmpty() || count > 0) result.add(count)
            count = line.count { it == '#' }
        } else if (line.isNotBlank()) {
            count += line.count { it == '#' }
        }
    }
    result.add(count)
    return result
}

fun parseAreas(input: List<String>): List<Pair<Pair<Int, Int>, List<Int>>> {
    val areaRegex = Regex("""(\d+)\s*x\s*(\d+):\s*([\d\s]+)""")
    val result = mutableListOf<Pair<Pair<Int, Int>, List<Int>>>()
    for (line in input) {
        val match = areaRegex.matchEntire(line.trim())
        if (match != null) {
            val n = match.groupValues[1].toInt()
            val m = match.groupValues[2].toInt()
            val numbers = match.groupValues[3].trim().split(Regex("\\s+")).map { it.toInt() }
            result.add((n to m) to numbers)
        }
    }
    return result
}