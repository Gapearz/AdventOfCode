package Day02_GiftShop

import kotlin.math.log10

fun main(){
    val fileReader = Tools.FileReaderAdvent("src/Day02_GiftShop/input.txt")
    val input = fileReader.readFileAsLine(",")
    println("[part 1] Sum of invalid IDs: ${part1(input)}")
    println("[part 2] Sum of invalid IDs: ${part2(input)}")
}

fun part1(input: List<String>): Long{
    var invalidIdSum = 0L

    for (line in input){
        var startId = line.split("-")[0].toLong()
        val endId = line.split("-")[1].toLong()

        while (startId <= endId){
            val log10Value = log10(startId.toDouble()).toInt() + 1
            if(log10Value % 2 == 0){
                val firstHalf = startId.toString().substring(0, log10Value / 2)
                val secondHalf = startId.toString().substring(log10Value / 2, log10Value)
                if (firstHalf == secondHalf){
                    invalidIdSum += startId
                }
            }

            startId++
        }
    }

    return invalidIdSum
}

fun part2(input: List<String>): Long {
    var invalidIdSum = 0L

    for (line in input) {
        var startId = line.split("-")[0].toLong()
        val endId = line.split("-")[1].toLong()

        while (startId <= endId) {
            val startIdString = startId.toString()
            val len = startIdString.length

            for (subLen in 1..len / 2) {
                if (len % subLen == 0) {
                    val pattern = startIdString.take(subLen)
                    val repeated = pattern.repeat(len / subLen)
                    if (repeated == startIdString) {
                        invalidIdSum += startId
                        break
                    }
                }
            }
            startId++
        }

    }

    return invalidIdSum
}
