package Day08_Playground

import kotlin.math.pow
import kotlin.math.sqrt

fun main(){
    val fileReaderAdvent = Tools.FileReaderAdvent("src/Day08_Playground/testInput.txt")
    val input = fileReaderAdvent.readFile()

    val positions = input.map {
        val coords = it.split(",")
        Triple(coords[0].toInt(), coords[1].toInt(), coords[2].toInt())
    }

    println("[part 1] Multi of biggest 3: ${part1(positions)}")
}

fun part1(positions: List<Triple<Int, Int, Int>>): Long{
    var multiOfLargest = 1L
    var distanceMap = mutableMapOf<Pair<Int, Int>, Double>()

    for(i in 0 ..< positions.size - 1){
        for(j in i + 1 ..< positions.size){
            distanceMap[i to j] = getDistance(positions[i], positions[j])
        }
    }

    println(distanceMap.values.sorted().take(1) + " " + distanceMap.entries.firstOrNull { it.value == distanceMap.values.sorted()[0]}?.key)

    return multiOfLargest
}

fun part2(){

}

fun getDistance(a: Triple<Int, Int, Int>, b: Triple<Int, Int, Int>): Double {
    return sqrt(((a.first - b.first).toDouble()).pow(2) + ((a.second - b.second).toDouble()).pow(2) + ((a.third - b.third).toDouble()).pow(2))
}