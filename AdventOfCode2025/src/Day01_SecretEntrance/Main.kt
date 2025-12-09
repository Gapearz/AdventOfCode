package Day01_SecretEntrance

import Tools.FileReaderAdvent
import kotlin.system.measureTimeMillis

fun main() {
    val fileReader = FileReaderAdvent("src/Day01_SecretEntrance/input.txt")
    val input = fileReader.readFile()

    val time1 = measureTimeMillis {
        val result = part1(input)
        println("[part 1] Times at position 0: $result")
    }
    println("Part 1 took: $time1 ms")

    val time2 = measureTimeMillis {
        val result = part2(input)
        println("[part 2] Times at position 0: $result")
    }
    println("Part 2 took: $time2 ms")

}

fun part1(input: List<String>): Int{
    var timesAtZero = 0
    var currentPosition: Int = 50
    for(line in input){
        if(line.startsWith("R")){
            currentPosition += line.substring(1).toInt()%100
            currentPosition %= 100
        } else if (line.startsWith("L")){
            currentPosition -= line.substring(1).toInt()%100
            if(currentPosition < 0){
                currentPosition += 100
            }
        }

        if(currentPosition == 0){
            timesAtZero++
        }
    }
    return timesAtZero
}

fun part2(input: List<String>): Int {
    var timesAtZero = 0
    var currentPosition = 50
    for (line in input) {
        var step = line.substring(1).toInt()
        timesAtZero += step.floorDiv(100)
        step %= 100
        if (line.startsWith("R")){
            currentPosition += step
            timesAtZero += currentPosition.floorDiv(100)
            currentPosition %= 100
        } else if (line.startsWith("L")){
            currentPosition -=  step
            if(currentPosition < 0){
                if (currentPosition + step != 0){
                    timesAtZero++
                }
                currentPosition += 100
            }
            if (currentPosition == 0){
                timesAtZero++
            }
        }
    }
    return timesAtZero
}