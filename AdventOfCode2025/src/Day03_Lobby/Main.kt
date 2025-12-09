package Day03_Lobby

import kotlin.math.pow
import kotlin.system.measureTimeMillis

fun main(){
    val fileReaderAdvent = Tools.FileReaderAdvent("src/Day03_Lobby/input.txt")
    val input = fileReaderAdvent.readFileAsIntMatrix()

    val time1 = measureTimeMillis {
        val result = part1(input)
        println("[part 1] Voltage sum: $result")
    }
    println("Part 1 took: $time1 ms")

    val time2 = measureTimeMillis {
        val result = part2(input)
        println("[part 2] Voltage sum: $result")
    }
    println("Part 2 took: $time2 ms")
}

fun part1(input : List<List<Int>>): Int{
    var voltageSum = 0

    for (line in input){
        val firstNumber = getHighestNumber(line.subList(0, line.size - 1))
        val positionOfFirstNumber = line.indexOf(firstNumber)
        val secondNumber = getHighestNumber(line.subList(positionOfFirstNumber + 1, line.size))
        voltageSum += (firstNumber * 10) + secondNumber
    }

    return voltageSum
}

fun part2(input : List<List<Int>>): Long{
    var voltageSum = 0L

    for (line in input){
        var digitsRequired = 12
        var remainingLine = line
        while(digitsRequired != 0){
            val nextNumber = getHighestNumber(remainingLine.subList(0, remainingLine.size - digitsRequired + 1))
            remainingLine = remainingLine.subList(remainingLine.indexOf(nextNumber) + 1, remainingLine.size)
            voltageSum += nextNumber * 10.0.pow((digitsRequired - 1).toDouble()).toLong()
            digitsRequired--
        }
    }

    return voltageSum
}

fun getHighestNumber(line: List<Int>): Int{
    var highestNumber = 0

    for(i in line){
        if(i > highestNumber){
            highestNumber = i
        }
    }

    return highestNumber
}